package com.example.tbilisi_parking_final_exm.presentation.event.parking.edit_vehicle

import com.google.android.material.textfield.TextInputLayout

sealed class EditVehicleEvent {
    data class EditVehicle(val vehicleId: Int, val name: String) : EditVehicleEvent()

    data  class SetButtonState(val fields: List<TextInputLayout>) : EditVehicleEvent()

//    data object ResetErrorMessage : EditVehicleEvent()



}