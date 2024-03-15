package com.example.tbilisi_parking_final_exm.domain.usecase.refresh_token

import com.example.tbilisi_parking_final_exm.domain.repository.refresh_token.RefreshTokenRepository
import javax.inject.Inject

class RefreshTokenUseCase  @Inject constructor(
    private val refreshTokenRepository: RefreshTokenRepository
){
    suspend operator fun invoke(token: String) = refreshTokenRepository.refreshToken(token = token)
}