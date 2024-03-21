package com.example.tbilisi_parking_final_exm.presentation.event.parking.active_licenses


sealed class ActiveLicensesEvent {
    data object ResetErrorMessage : ActiveLicensesEvent()
    data class GetActiveLicenses(val carId: Int) : ActiveLicensesEvent()
}