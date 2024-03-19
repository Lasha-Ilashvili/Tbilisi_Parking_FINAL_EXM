package com.example.tbilisi_parking_final_exm.presentation.event.license_cards.buy_license

import com.google.android.material.textfield.TextInputLayout


sealed class BuyLicenseEvent {
    data object ResetErrorMessage : BuyLicenseEvent()
    data class SetButtonState(val fields: List<TextInputLayout>) : BuyLicenseEvent()
    data class BuyLicense(
        val plateNumber: TextInputLayout,
        val personalNumber: TextInputLayout,
        val descriptionId: Int
    ) : BuyLicenseEvent()
}