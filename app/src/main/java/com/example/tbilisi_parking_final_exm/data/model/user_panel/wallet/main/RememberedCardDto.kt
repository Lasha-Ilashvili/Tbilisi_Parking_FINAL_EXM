package com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.main

import com.squareup.moshi.Json


data class RememberedCardDto(
    val id: Int,
    val cardNumber: String,
    @Json(name = "expiredDate") val date: String,
    val cvv: String
)