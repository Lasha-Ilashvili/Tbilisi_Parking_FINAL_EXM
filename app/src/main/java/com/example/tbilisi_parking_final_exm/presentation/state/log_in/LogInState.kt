package com.example.tbilisi_parking_final_exm.presentation.state.log_in

data class LogInState(
    val isLoading: Boolean = false,
    val token: String? = null,
    val errorMessage: String? = null,
    val isErrorEnabled: Boolean = false,
    val isButtonEnabled: Boolean = false
)
