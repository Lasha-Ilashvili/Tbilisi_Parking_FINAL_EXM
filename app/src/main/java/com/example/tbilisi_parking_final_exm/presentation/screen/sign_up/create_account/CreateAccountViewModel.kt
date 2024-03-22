package com.example.tbilisi_parking_final_exm.presentation.screen.sign_up.create_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.sign_up.SignUpUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.EmailValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.MatchingPasswordValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.PasswordValidatorUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.sign_up.create_account.CreateAccountEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.sign_up.toDomain
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
    private val passwordValidator: PasswordValidatorUseCase,
    private val matchingPasswordValidator: MatchingPasswordValidatorUseCase
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
        val isPasswordValid = passwordValidator(passwordInput)
        val isMatchingPasswordValid =
            matchingPasswordValidator(passwordInput, matchingPasswordInput)

        val areFieldsValid =
            listOf(isEmailValid, isPasswordValid.first, isMatchingPasswordValid.first)
                .all { it }

        validateField(isEmailValid, email)
        validateField(isPasswordValid.first, password, isPasswordValid.second)
        validateField(isMatchingPasswordValid.first, matchingPassword,isMatchingPasswordValid.second)

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

    private fun validateField(
        isFieldValid: Boolean,
        textInputLayout: TextInputLayout,
        inputErrorMessage: Int = R.string.invalid_input
    ) {
        updateErrorTextInputLayout(
            errorTextInputLayout = textInputLayout,
            isErrorEnabled = !isFieldValid,
            inputErrorMessage = inputErrorMessage
        )
    }

    private fun updateErrorTextInputLayout(
        errorTextInputLayout: TextInputLayout,
        isErrorEnabled: Boolean,
        inputErrorMessage: Int
    ) {
        _createAccountState.update { currentState ->
            currentState.copy(
                errorTextInputLayout = errorTextInputLayout,
                isErrorEnabled = isErrorEnabled,
                inputErrorMessage = inputErrorMessage
            )
        }
    }

    private fun signUp(user: User) {
        viewModelScope.launch {
            signUpUseCase(user.toDomain()).collect {
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
                    else -> {}

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