package com.example.tbilisi_parking_final_exm.presentation.state.licenses.buy_license

import com.google.android.material.textfield.TextInputLayout


data class BuyLicenseState(
    val errorTextInputLayout: TextInputLayout? = null,
    val isErrorEnabled: Boolean = false,
    val isButtonEnabled: Boolean = false
)