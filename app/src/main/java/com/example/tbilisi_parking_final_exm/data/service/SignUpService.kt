package com.example.tbilisi_parking_final_exm.data.service

import com.example.tbilisi_parking_final_exm.data.model.ApiResponseDto
import com.example.tbilisi_parking_final_exm.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("register")
    suspend fun signUp(@Body user: User): Response<ApiResponseDto>
}