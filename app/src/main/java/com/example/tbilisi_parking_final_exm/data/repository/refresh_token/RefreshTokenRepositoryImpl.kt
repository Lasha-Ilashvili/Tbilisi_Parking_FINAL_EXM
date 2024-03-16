package com.example.tbilisi_parking_final_exm.data.repository.refresh_token

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.new_token.toDomain
import com.example.tbilisi_parking_final_exm.data.service.refresh_token.RefreshTokenService
import com.example.tbilisi_parking_final_exm.domain.model.new_token.GetNewToken
import com.example.tbilisi_parking_final_exm.domain.repository.refresh_token.RefreshTokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshTokenRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val refreshTokenService: RefreshTokenService
) : RefreshTokenRepository {
    override suspend fun refreshToken(token: String):Flow<Resource<GetNewToken>> {
        return handleResponse.safeApiCall {
            refreshTokenService.refreshToken(grantType = "refresh_token", refreshToken = token)
        }.asResource {
            it.toDomain()
        }
    }
}
