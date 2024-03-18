package com.example.tbilisi_parking_final_exm.presentation.event.licenses.buy_license

import com.google.android.material.textfield.TextInputLayout


sealed class BuyLicenseEvent {
    data class SetButtonState(val fields: List<TextInputLayout>) : BuyLicenseEvent()
}