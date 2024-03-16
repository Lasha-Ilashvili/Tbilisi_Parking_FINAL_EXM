package com.example.tbilisi_parking_final_exm.data.service.user_panel.profile

import com.example.tbilisi_parking_final_exm.data.model.user_panel.profile.ProfileDto
import retrofit2.Response
import retrofit2.http.GET

interface ProfileService {

    @GET("user/context")
    suspend fun getUserProfile(): Response<ProfileDto>
}