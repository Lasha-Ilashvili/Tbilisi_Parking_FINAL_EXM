package com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.balance

import com.google.android.material.textfield.TextInputLayout


data class BalanceState(
    val isLoading:Boolean = false,
    val errorTextInputLayout: TextInputLayout? = null,
    val isErrorEnabled: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val errorMessage: String? = null
)