package com.example.tbilisi_parking_final_exm.domain.model.log_in


data class GetToken(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String,
    val tokenType: String,
    val expiresIn: Int
)