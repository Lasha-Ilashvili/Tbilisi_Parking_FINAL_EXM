package com.example.tbilisi_parking_final_exm.data.service.log_in

import com.example.tbilisi_parking_final_exm.data.model.log_in.LogInDto
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface LogInService {
    @POST("oauth2/token")
    suspend fun logIn(
        @Query("grant_type") grantType: String,
        @Query("scope") scope: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<LogInDto>
}