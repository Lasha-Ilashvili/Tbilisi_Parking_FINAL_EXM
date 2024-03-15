package com.example.tbilisi_parking_final_exm.data.model.new_token

import com.squareup.moshi.Json

data class NewTokenDto(
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "refresh_token")
    val refreshToken: String,
    @Json(name = "scope")
    val scope : String,
    @Json(name = "id_token")
    val idToken: String,
    @Json(name = "token_type")
    val tokenType: String,
    @Json(name = "expires_in")
    val expiresIn: Int


)