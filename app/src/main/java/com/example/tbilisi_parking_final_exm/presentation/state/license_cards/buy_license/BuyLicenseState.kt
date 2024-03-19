package com.example.tbilisi_parking_final_exm.presentation.state.license_cards.buy_license

import com.google.android.material.textfield.TextInputLayout


data class BuyLicenseState(
    val isLoading: Boolean = false,
    val errorTextInputLayout: TextInputLayout? = null,
    val isErrorEnabled: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val errorMessage: String? = null
)