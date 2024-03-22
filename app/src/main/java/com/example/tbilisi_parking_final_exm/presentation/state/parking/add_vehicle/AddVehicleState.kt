package com.example.tbilisi_parking_final_exm.presentation.state.parking.add_vehicle

import com.google.android.material.textfield.TextInputLayout

data class AddVehicleState(
    val isLoading: Boolean = false,
    val errorTextInputLayout: TextInputLayout? = null,
    val errorMessage: String? = null,
    val isButtonEnabled: Boolean = false,
    val isErrorEnabled: Boolean = false,
    val sessionCompleted: Boolean = false
)
