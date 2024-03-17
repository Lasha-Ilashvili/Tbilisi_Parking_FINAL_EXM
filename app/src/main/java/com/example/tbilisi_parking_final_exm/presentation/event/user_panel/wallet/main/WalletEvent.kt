package com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.main

import com.google.android.material.textfield.TextInputLayout


sealed class WalletEvent {
    data class SetButtonState(val field: TextInputLayout) : WalletEvent()
    data object GetRememberedCards : WalletEvent()
    data object GetBalance : WalletEvent()
    data object ResetErrorMessage : WalletEvent()
}