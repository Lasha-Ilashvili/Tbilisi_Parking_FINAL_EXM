package com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.balance


data class AddBalanceRequest(
    val userId: Int,
    val cardId: Int?,
    val amount: Int,
)