package com.example.tbilisi_parking_final_exm.presentation.screen.sign_up.create_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.sign_up.SignUpUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.EmailValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.SignUpPasswordValidatorUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.sign_up.create_account.CreateAccountEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.sign_up.toPresentation
import com.example.tbilisi_parking_final_exm.presentation.model.sign_up.User
import com.example.tbilisi_parking_final_exm.presentation.model.sign_up.UserCredentials
import com.example.tbilisi_parking_final_exm.presentation.state.sign_up.create_account.CreateAccountState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val emailValidator: EmailValidatorUseCase,
    private val passwordValidator: SignUpPasswordValidatorUseCase
) : ViewModel() {

    private val _createAccountState = MutableStateFlow(CreateAccountState())
    val createAccountState get() = _createAccountState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CreateAccountUiEvent>()
    val uiEvent get() = _uiEvent

    fun onEvent(event: CreateAccountEvent) = with(event) {
        when (this) {
            is CreateAccountEvent.SetButtonState -> setButtonState(fields = fields)

            is CreateAccountEvent.SignUp -> validateFields(
                firstName = firstName,
                lastName = lastName,
                email = email,
                mobileNumber = mobileNumber,
                password = password,
                matchingPassword = matchingPassword,
                personalNumber = personalNumber
            )

            CreateAccountEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun validateFields(
        firstName: String,
        lastName: String,
        email: TextInputLayout,
        mobileNumber: String,
        password: TextInputLayout,
        matchingPassword: TextInputLayout,
        personalNumber: String
    ) {
        val emailInput = email.editText?.text.toString()
        val passwordInput = password.editText?.text.toString()
        val matchingPasswordInput = matchingPassword.editText?.text.toString()

        val isEmailValid = emailValidator(emailInput)
        val isPasswordValid = passwordValidator(passwordInput, matchingPasswordInput)

        val areFieldsValid =
            listOf(isEmailValid, isPasswordValid)
                .all { it }

        validateField(isEmailValid, email)
        validateField(isPasswordValid, password)
        validateField(isPasswordValid, matchingPassword)

        if (!areFieldsValid) {
            return
        }

        val user = User(
            firstName = firstName,
            lastName = lastName,
            email = emailInput,
            mobileNumber = mobileNumber,
            password = passwordInput,
            matchingPassword = matchingPasswordInput,
            personalNumber = personalNumber
        )

        signUp(user)
    }

    private fun validateField(isFieldValid: Boolean, textInputLayout: TextInputLayout) {
        updateErrorTextInputLayout(
            errorTextInputLayout = textInputLayout,
            isErrorEnabled = !isFieldValid
        )
    }

    private fun updateErrorTextInputLayout(
        errorTextInputLayout: TextInputLayout,
        isErrorEnabled: Boolean
    ) {
        _createAccountState.update { currentState ->
            currentState.copy(
                errorTextInputLayout = errorTextInputLayout,
                isErrorEnabled = isErrorEnabled
            )
        }
    }

    private fun signUp(user: User) {
        viewModelScope.launch {
            signUpUseCase(user).collect {
                when (it) {
                    is Resource.Success -> {
                        _uiEvent.emit(
                            CreateAccountUiEvent.NavigateBackToLogIn(
                                userCredentials = it.data.toPresentation()
                            )
                        )
                    }

                    is Resource.Loading -> _createAccountState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun setButtonState(fields: List<TextInputLayout>) {
        _createAccountState.update { currentState ->
            currentState.copy(isButtonEnabled = fieldsAreNotBlank(fields))
        }
    }



    private fun updateErrorMessage(message: String? = null) {
        _createAccountState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    sealed interface CreateAccountUiEvent {
        data class NavigateBackToLogIn(val userCredentials: UserCredentials) : CreateAccountUiEvent
    }
}