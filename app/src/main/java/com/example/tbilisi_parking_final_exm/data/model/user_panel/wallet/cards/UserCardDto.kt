package com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.cards

import com.squareup.moshi.Json


data class UserCardDto(
    val id: Int,
    val cardNumber: String,
    @Json(name = "expiredDate") val date: String,
    val cvv: String
)