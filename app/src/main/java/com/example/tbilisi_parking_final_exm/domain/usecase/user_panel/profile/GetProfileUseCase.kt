package com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.profile

import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.profile.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke() = profileRepository.getProfile()
}