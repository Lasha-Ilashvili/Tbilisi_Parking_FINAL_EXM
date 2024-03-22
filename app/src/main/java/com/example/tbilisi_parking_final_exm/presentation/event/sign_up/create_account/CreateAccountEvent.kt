package com.example.tbilisi_parking_final_exm.presentation.event.sign_up.create_account

import com.google.android.material.textfield.TextInputLayout


sealed class CreateAccountEvent {
    data object ResetErrorMessage : CreateAccountEvent()
    data class SetButtonState(val fields: List<TextInputLayout>) : CreateAccountEvent()
    data class SignUp(
        val firstName: String,
        val lastName: String,
        val email: TextInputLayout,
        val mobileNumber: String,
        val password: TextInputLayout,
        val matchingPassword: TextInputLayout,
        val personalNumber: String
    ) : CreateAccountEvent()
    data class SetPasswordStrengthState(val password: String) : CreateAccountEvent()
}