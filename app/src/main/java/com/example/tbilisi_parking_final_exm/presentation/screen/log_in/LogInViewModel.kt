package com.example.tbilisi_parking_final_exm.presentation.screen.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.DataStoreUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.log_in.LogInUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.profile.GetProfileUseCase
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
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val getProfile: GetProfileUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _logInState = MutableStateFlow(LogInState())
    val logInState: SharedFlow<LogInState> = _logInState.asStateFlow()


    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent get() = _uiEvent

    private var isSessionSaved: Boolean = false

    fun onEvent(event: LogInEvent) = with(event) {
        when (this) {
            is LogInEvent.LogIn -> validateForms(email = email, password = password)
            is LogInEvent.SetButtonState -> setButtonState(fields = fields)
            is LogInEvent.ResetErrorMessage -> updateErrorMessage(message = null)
            is LogInEvent.SaveSession -> setSession(isChecked = isChecked)
        }
    }

    private fun validateForms(email: TextInputLayout, password: TextInputLayout) {
        val emailInput = email.editText?.text.toString()
        val passwordInput = password.editText?.text.toString()


        val isEmailValid = emailValidator(email = emailInput)
        val isPasswordValid = passwordValidator(password = passwordInput)

        updateErrorTextInputLayout(email, isEmailValid)
        updateErrorTextInputLayout(password, isPasswordValid)


        if (isEmailValid && isPasswordValid) {
            logIn(email = emailInput, password = passwordInput)
        }
    }

    private fun updateErrorTextInputLayout(
        errorTextInputLayout: TextInputLayout,
        isFieldValid: Boolean
    ) {
        _logInState.update { currentState ->
            currentState.copy(
                errorTextInputLayout = errorTextInputLayout,
                isErrorEnabled = !isFieldValid
            )
        }
    }

    private fun logIn(email: String, password: String) {
        viewModelScope.launch {
            logInUseCase(email = email, password = password).collect {
                when (it) {
                    is Resource.Success -> {
                       dataStoreUseCase.saveAccessToken(it.data.accessToken)
                        dataStoreUseCase.saveRefreshToken(it.data.refreshToken)
                        if (isSessionSaved) dataStoreUseCase.saveSession.invoke()

                        getUserId()
                    }

                    is Resource.Error -> updateErrorMessage(it.errorMessage)

                    is Resource.Loading -> _logInState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }
                }
            }
        }
    }

    private suspend fun getUserId() {
        getProfile().collect {
            when (it) {
                is Resource.Success -> {
                    dataStoreUseCase.saveUserId(it.data.id)
                    _uiEvent.emit(LoginUiEvent.NavigateToParkingFragment)
                }

                is Resource.Error -> updateErrorMessage(it.errorMessage)

                is Resource.Loading -> _logInState.update { currentState ->
                    currentState.copy(
                        isLoading = it.loading
                    )
                }
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

    private fun updateErrorMessage(message: String?) {
        _logInState.update { currentState ->
            currentState.copy(
                errorMessage = message
            )
        }
    }

    private fun setSession(isChecked: Boolean) {
        isSessionSaved = isChecked
    }

    sealed interface LoginUiEvent {
        data object NavigateToParkingFragment : LoginUiEvent
    }

}