package com.example.tbilisi_parking_final_exm.data.service.sign_up

import com.example.tbilisi_parking_final_exm.data.model.sign_up.SignUpDto
import com.example.tbilisi_parking_final_exm.data.model.sign_up.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("user/registration")
    suspend fun signUp(@Body user: UserDto): Response<SignUpDto>
}