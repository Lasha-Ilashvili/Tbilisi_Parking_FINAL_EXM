package com.example.tbilisi_parking_final_exm.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponseDto(
    val token: String
)