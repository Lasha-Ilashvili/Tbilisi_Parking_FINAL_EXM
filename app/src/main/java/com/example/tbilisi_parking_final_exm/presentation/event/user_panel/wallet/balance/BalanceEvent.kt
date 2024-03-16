package com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.balance

import com.google.android.material.textfield.TextInputLayout


sealed class BalanceEvent {
    data object ResetErrorMessage : BalanceEvent()
    data class SetButtonState(val fields: List<TextInputLayout>) : BalanceEvent()
    data class ProceedToPayment(
        val cardNumber: TextInputLayout,
        val date: TextInputLayout,
        val cvv: TextInputLayout,
        val isRememberCardChecked: Boolean
    ) : BalanceEvent()
}