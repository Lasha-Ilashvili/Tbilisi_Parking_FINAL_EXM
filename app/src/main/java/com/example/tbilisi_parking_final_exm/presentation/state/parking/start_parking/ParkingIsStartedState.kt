package com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking

import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.balance.Balance

data class ParkingIsStartedState(
    val isLoading: Boolean = false,
    val balance: Balance? = null,
    val errorMessage: String? = null,
    val sessionCompleted: Boolean = false
)