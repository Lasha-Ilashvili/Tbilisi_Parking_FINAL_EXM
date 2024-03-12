package com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet

import com.google.android.material.textfield.TextInputLayout


sealed class WalletEvent {
    data class SetCardLayoutState(val field: TextInputLayout) : WalletEvent()
    data class SetButtonState(val fields: List<TextInputLayout>) : WalletEvent()
    data class SignUp(
        val firstName: String,
        val lastName: String,
        val email: TextInputLayout,
        val mobileNumber: String,
        val password: TextInputLayout,
        val matchingPassword: TextInputLayout,
        val personalNumber: String
    ) : WalletEvent()
}