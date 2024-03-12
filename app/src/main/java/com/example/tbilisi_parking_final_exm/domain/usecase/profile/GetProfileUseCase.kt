package com.example.tbilisi_parking_final_exm.domain.usecase.profile

import com.example.tbilisi_parking_final_exm.domain.repository.profile.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke() = profileRepository.getProfile()
}