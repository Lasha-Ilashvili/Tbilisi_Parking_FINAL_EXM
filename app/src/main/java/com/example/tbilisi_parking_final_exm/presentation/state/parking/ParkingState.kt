package com.example.tbilisi_parking_final_exm.presentation.state.parking

import com.example.tbilisi_parking_final_exm.presentation.model.parking.active_parking.ActiveParking
import com.example.tbilisi_parking_final_exm.presentation.model.parking.vehicle.vehicle.Vehicle
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.balance.Balance

data class ParkingState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val vehicles: List<Vehicle>? = null,
    val balance: Balance? = null,
    val activeParking: List<ActiveParking>? = null,
    val sessionCompleted: Boolean = false
)