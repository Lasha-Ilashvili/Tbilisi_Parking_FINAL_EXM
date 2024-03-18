package com.example.tbilisi_parking_final_exm.presentation.event.licenses.all_licenses


sealed class LicensesEvent {
    data object GetLicenses : LicensesEvent()
    data object ResetErrorMessage : LicensesEvent()
}