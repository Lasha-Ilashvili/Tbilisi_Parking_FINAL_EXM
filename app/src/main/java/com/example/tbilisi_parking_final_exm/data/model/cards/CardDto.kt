package com.example.tbilisi_parking_final_exm.data.model.cards

import com.squareup.moshi.Json


data class CardDto(
    val title: String,
    @Json(name = "zonal_card")
    val zonalCard: ZonalCardDto? = null
) {
    data class ZonalCardDto(
        val period: String,
        val price: Int,
        @Json(name = "background_color")
        val backgroundColor: String
    )
}
