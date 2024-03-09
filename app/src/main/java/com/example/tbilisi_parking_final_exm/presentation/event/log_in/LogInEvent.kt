package com.example.tbilisi_parking_final_exm.presentation.event.log_in

import com.google.android.material.textfield.TextInputLayout

sealed class LogInEvent {
    data class LogIn(val email: TextInputLayout, val password: TextInputLayout): LogInEvent()

    data  class SetButtonState(val fields: List<TextInputLayout>) :LogInEvent()

    data object ResetErrorMessage :LogInEvent()

}