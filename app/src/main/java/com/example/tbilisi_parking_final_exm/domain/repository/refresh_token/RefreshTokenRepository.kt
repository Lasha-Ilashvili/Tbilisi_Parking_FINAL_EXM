package com.example.tbilisi_parking_final_exm.domain.repository.refresh_token

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.new_token.GetNewToken
import kotlinx.coroutines.flow.Flow

interface RefreshTokenRepository  {
    suspend fun refreshToken(token: String) : Flow<Resource<GetNewToken>>
}