package com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking

import com.example.tbilisi_parking_final_exm.presentation.screen.parking.start_parking.StartParkingFragment

data class StartParkingState(
    val isButtonEnabled: Boolean = false,
    val isCostLayoutEnabled: Boolean = false,
    val zone: StartParkingFragment.Zone = StartParkingFragment.Zone.A
)