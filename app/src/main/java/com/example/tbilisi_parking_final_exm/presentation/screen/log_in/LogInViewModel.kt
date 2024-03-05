package com.example.tbilisi_parking_final_exm.presentation.screen.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.log_in.LogInUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.EmailValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.LogInPasswordValidatorUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.log_in.LogInEvent
import com.example.tbilisi_parking_final_exm.presentation.state.log_in.LogInState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val emailValidator: EmailValidatorUseCase,
    private val passwordValidator: LogInPasswordValidatorUseCase,
    private val logInUseCase: LogInUseCase,
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase
) : ViewModel() {

    private val _logInState = MutableStateFlow(LogInState())
    val logInState: SharedFlow<LogInState> = _logInState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LogInUiEvent>()
    val uiEvent get() = _uiEvent

    fun onEvent(event: LogInEvent) = with(event) {
        when (this) {
            is LogInEvent.LogIn -> validateForms(email = email, password = password)
            is LogInEvent.SetButtonState -> setButtonState(fields = fields)
        }
    }

    private fun validateForms(email: String, password: String) {
        val isEmailValid = emailValidator(email = email)
        val isPasswordValid = passwordValidator(password = password)

        if (isEmailValid && isPasswordValid) {
            logIn(email = email, password = password)
        } else {
            println("incorrect inputs")
        }
    }

    private fun logIn(email: String, password: String) {
        viewModelScope.launch {
            logInUseCase(email = email, password = password).collect {
                println("this is resource in logIn viewModel -> $it")
                when (it) {
                    is Resource.Success -> _uiEvent.emit(LogInUiEvent.NavigateToNestedNavGraph)

                    is Resource.Error -> _logInState.update { currentState ->
                        currentState.copy()
                    }

                    is Resource.Loading -> _logInState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }
                }
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

    private fun setButtonState(fields: List<TextInputLayout>) {
        _logInState.update { currentState ->
            currentState.copy(
                isButtonEnabled = fieldsAreNotBlank(fields)
            )
        }
    }

    sealed interface LogInUiEvent {
        data object NavigateToNestedNavGraph : LogInUiEvent
    }
}