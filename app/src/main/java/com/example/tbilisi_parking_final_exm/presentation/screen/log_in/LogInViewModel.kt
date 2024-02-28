package com.example.tbilisi_parking_final_exm.presentation.screen.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.domain.usecase.log_in.LogInUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.EmailValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.LogInPasswordValidatorUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.log_in.LogInEvent
import com.example.tbilisi_parking_final_exm.presentation.state.log_in.LogInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val emailValidatorUseCase: EmailValidatorUseCase,
    private val passwordValidatorUseCase: LogInPasswordValidatorUseCase,
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    private val _logInState = MutableStateFlow(LogInState())
    val logInState: SharedFlow<LogInState> = _logInState.asStateFlow()
    fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.LogIn -> validateForms(email = event.email, password = event.password)
        }
    }

    private fun validateForms(email: String, password: String) {
        val isEmailValid = emailValidatorUseCase(email = email)
        val isPasswordValid = passwordValidatorUseCase(password = password)


        if (isEmailValid && isPasswordValid) {
            logIn(email = email, password = password)
        } else {
            println("incorrect inputs")
        }
    }

    private fun logIn(email: String, password: String) {
        viewModelScope.launch {
            logInUseCase(email = email, password = password).collect{
                println("this is resource in logIn viewModel -> $it")
//                when (it) {
//                    is Resource.Success -> _logInState.update {currentState ->
//                        currentState.copy(
//                            token = it.data
//                        )
//                    }
//
//                    is Resource.Loading ->  _logInState.update {currentState ->
//                        currentState.copy(
//                            isLoading = it.loading
//                        )
//                    }
//
//                    is Resource.Error -> _logInState.update { currentState ->
//                        currentState.copy(
//                            errorMessage = it.errorMessage
//                        )
//                    }
//                }
            }
        }
    }
}