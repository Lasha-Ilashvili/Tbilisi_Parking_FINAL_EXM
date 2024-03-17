package com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.main


data class GetRememberedCard(
    val id: Int,
    val cardNumber: String,
    val date: String,
    val cvv: String
)
