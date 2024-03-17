package com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.balance


data class AddBalanceRequestDto(
    val userId: Int,
    val cardId: Int?,
    val amount: Int,
)