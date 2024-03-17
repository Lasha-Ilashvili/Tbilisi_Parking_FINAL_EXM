package com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.cards


data class GetUserCard(
    val id: Int,
    val cardNumber: String,
    val date: String,
    val cvv: String
)
