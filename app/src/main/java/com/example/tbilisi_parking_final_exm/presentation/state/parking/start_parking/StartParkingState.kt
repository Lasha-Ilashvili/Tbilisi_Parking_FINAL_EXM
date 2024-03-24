package com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking

import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.presentation.model.parking.start_parking.ParkingIsStarted
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.balance.Balance

data class StartParkingState(
    val isLoading: Boolean = false,
    val balance: Balance? = null,
    val isButtonEnabled: Boolean = false,
    val isCostLayoutEnabled: Boolean = false,
    val zone: Zone = Zone.A,
    val errorMessage: String? = null,
    val data: ParkingIsStarted? = null,
    val sessionCompleted: Boolean = false
)

enum class Zone(val cost: Int, val color: Int, val icon: Int, val value: String) {
    A(1, R.color.dark_blue, R.drawable.ic_letter_a, "A"),
    B(2, R.color.yellow, R.drawable.ic_letter_b, "B"),
    C(3, R.color.green, R.drawable.ic_letter_c, "C")
}