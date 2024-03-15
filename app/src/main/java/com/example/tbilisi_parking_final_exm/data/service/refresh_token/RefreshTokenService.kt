package com.example.tbilisi_parking_final_exm.data.service.refresh_token

import com.example.tbilisi_parking_final_exm.data.model.new_token.NewTokenDto
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface RefreshTokenService {

    @POST("oauth2/token")
    suspend fun refreshToken(
        @Query("grant_type") grantType: String,
        @Query("refresh_token") refreshToken: String
    ): Response<NewTokenDto>
}