package com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet

import com.google.android.material.textfield.TextInputLayout


data class WalletState(
    val errorTextInputLayout: TextInputLayout? = null,
    val isErrorEnabled: Boolean = false,
    val isCardLayoutEnabled: Boolean = false,
    val isButtonEnabled:Boolean = false
)