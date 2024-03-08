package com.example.tbilisi_parking_final_exm.data.model.map

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class MarkerLocationDto(
    @Json(name = "lot_number") val lotNumber: String,
    val address: String
)