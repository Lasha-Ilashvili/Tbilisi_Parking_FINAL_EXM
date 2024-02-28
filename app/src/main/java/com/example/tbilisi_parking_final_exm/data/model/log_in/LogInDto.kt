package com.example.tbilisi_parking_final_exm.data.model.log_in

import com.squareup.moshi.Json

data class LogInDto(
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "refresh_token")
    val refreshToken: String,
    @Json(name = "id_token")
    val idToken: String,
    @Json(name = "token_type")
    val tokenType: String,
    @Json(name = "expires_in")
    val expiresIn: Int

)