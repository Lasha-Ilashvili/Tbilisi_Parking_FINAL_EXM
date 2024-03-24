package com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking

import com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking.Zone
import com.google.android.material.textfield.TextInputLayout

sealed class StartParkingEvent {
    data object GetBalance : StartParkingEvent()
    data class SetButtonState(val field: TextInputLayout) : StartParkingEvent()
    data class SetCostLayoutState(val isCostLayoutEnabled: Boolean = true) : StartParkingEvent()
    data class SetZoneState(val zone: Zone = Zone.A) :
        StartParkingEvent()

    data object ResetErrorMessage : StartParkingEvent()

    data class StartParking(val stationExternalId: String, val carId: Int): StartParkingEvent()
}