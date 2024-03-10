package com.example.tbilisi_parking_final_exm.presentation.event.add_vehicle

import com.google.android.material.textfield.TextInputLayout

sealed class AddVehicleEvent {
    data class AddVehicle(val name: TextInputLayout, val plateNumber: TextInputLayout) : AddVehicleEvent()
    data class SetButtonState(val fields: List<TextInputLayout>):AddVehicleEvent()

    data object ResetErrorMessage : AddVehicleEvent()

}
