package com.example.tbilisi_parking_final_exm.presentation.event.log_in

sealed class LogInEvents {
    data class LogIn(val email: String, val password: String): LogInEvents()
}