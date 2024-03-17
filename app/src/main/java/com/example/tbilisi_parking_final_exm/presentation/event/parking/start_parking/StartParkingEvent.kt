package com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking

import com.example.tbilisi_parking_final_exm.presentation.screen.parking.start_parking.StartParkingFragment
import com.google.android.material.textfield.TextInputLayout

sealed class StartParkingEvent {
    data object GetBalance : StartParkingEvent()
    data class SetButtonState(val field: TextInputLayout) : StartParkingEvent()
    data class SetCostLayoutState(val isCostLayoutEnabled: Boolean = true) : StartParkingEvent()
    data class SetZoneState(val zone: StartParkingFragment.Zone = StartParkingFragment.Zone.A) : StartParkingEvent()
    data object ResetErrorMessage : StartParkingEvent()

}