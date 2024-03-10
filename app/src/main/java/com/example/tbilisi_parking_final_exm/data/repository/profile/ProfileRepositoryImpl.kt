package com.example.tbilisi_parking_final_exm.data.repository.profile

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.profile.toDomain
import com.example.tbilisi_parking_final_exm.data.service.profile.ProfileService
import com.example.tbilisi_parking_final_exm.domain.model.profile.GetProfile
import com.example.tbilisi_parking_final_exm.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val profileService: ProfileService
) : ProfileRepository {
    override suspend fun getProfile(): Flow<Resource<GetProfile>> {
        return handleResponse.safeApiCall {
            profileService.getUserProfile()
        }.asResource {
            it.toDomain()
        }
    }
}