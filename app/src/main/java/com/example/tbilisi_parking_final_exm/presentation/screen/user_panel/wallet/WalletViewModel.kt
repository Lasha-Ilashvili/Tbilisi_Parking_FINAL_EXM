package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet

import androidx.lifecycle.ViewModel
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.EmailValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.SignUpPasswordValidatorUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.WalletEvent
import com.example.tbilisi_parking_final_exm.presentation.model.sign_up.User
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.WalletState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class WalletViewModel @Inject constructor(
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val emailValidator: EmailValidatorUseCase,
    private val passwordValidator: SignUpPasswordValidatorUseCase
) : ViewModel() {

    private val _walletState = MutableStateFlow(WalletState())
    val walletState get() = _walletState.asStateFlow()

    fun onEvent(event: WalletEvent) = with(event) {
        when (this) {
            is WalletEvent.SetCardLayoutState -> setCardLayoutState(field = field)

            is WalletEvent.SetButtonState -> setButtonState(fields = fields)

            is WalletEvent.SignUp -> validateFields(
                firstName = firstName,
                lastName = lastName,
                email = email,
                mobileNumber = mobileNumber,
                password = password,
                matchingPassword = matchingPassword,
                personalNumber = personalNumber
            )
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
        _walletState.update { currentState ->
            currentState.copy(
                errorTextInputLayout = errorTextInputLayout,
                isErrorEnabled = isErrorEnabled
            )
        }
    }

    private fun signUp(user: User) {

    }

    private fun setCardLayoutState(field: TextInputLayout) {
        _walletState.update { currentState ->
            currentState.copy(isCardLayoutEnabled = fieldsAreNotBlank(listOf(field)))
        }
    }

    private fun setButtonState(fields: List<TextInputLayout>) {
        _walletState.update { currentState ->
            currentState.copy(isButtonEnabled = fieldsAreNotBlank(fields))
        }
    }
}