package com.example.tbilisi_parking_final_exm.data.service

import com.example.tbilisi_parking_final_exm.data.model.log_in.LogInDto
import com.example.tbilisi_parking_final_exm.data.model.log_in.UserDto
import com.example.tbilisi_parking_final_exm.domain.model.log_in.GetUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

//const val GRANT_TYPE: String = "custom_password"
interface LogInService {

    @POST("oauth2/token")
    suspend fun logIn(
        @Query("grant_type") grantType: String,
        @Query("scope") scope: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<LogInDto>

}