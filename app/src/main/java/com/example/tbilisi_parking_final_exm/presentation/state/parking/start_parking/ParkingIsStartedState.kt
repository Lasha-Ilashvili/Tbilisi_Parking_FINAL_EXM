package com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking

data class ParkingIsStartedState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)