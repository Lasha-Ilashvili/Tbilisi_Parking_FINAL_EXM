package com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet

import com.google.android.material.textfield.TextInputLayout


sealed class WalletEvent {
    data class SetPayNowButtonState(val field: TextInputLayout) : WalletEvent()
    data class SetProceedToPaymentButtonState(val fields: List<TextInputLayout>) : WalletEvent()
    data class SetCardLayoutState(val isCardLayoutEnabled: Boolean = true) : WalletEvent()
    data class ProceedToPayment(
        val cardNumber: TextInputLayout,
        val date: TextInputLayout,
        val cvv: TextInputLayout
    ) : WalletEvent()
}