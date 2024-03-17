package com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance


data class GetAddBalanceRequest(
    val userId: Int,
    val cardId: Int?,
    val amount: Int,
)