package com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.balance

import com.squareup.moshi.Json


data class CardDetailsDto(
    val userId: Int,
    val cardNumber: String,
    @Json(name = "expiredDate") val date: String,
    val cvv: String
)