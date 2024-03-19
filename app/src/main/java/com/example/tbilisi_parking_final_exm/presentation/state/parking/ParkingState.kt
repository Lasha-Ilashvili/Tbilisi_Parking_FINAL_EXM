package com.example.tbilisi_parking_final_exm.presentation.state.parking

import com.example.tbilisi_parking_final_exm.presentation.model.parking.vehicle.vehicle.Vehicle

data class ParkingState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val vehicles: List<Vehicle>? = null
)