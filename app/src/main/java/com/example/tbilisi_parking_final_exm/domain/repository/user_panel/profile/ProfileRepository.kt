package com.example.tbilisi_parking_final_exm.domain.repository.user_panel.profile

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.profile.GetProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(): Flow<Resource<GetProfile>>
}