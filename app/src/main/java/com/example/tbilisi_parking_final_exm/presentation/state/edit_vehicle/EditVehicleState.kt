package com.example.tbilisi_parking_final_exm.presentation.state.edit_vehicle

import com.google.android.material.textfield.TextInputLayout

data class EditVehicleState (
    val isLoading: Boolean = false,
    val errorTextInputLayout: TextInputLayout? = null,
    val errorMessage: String? = null,
    val isButtonEnabled: Boolean = false


)