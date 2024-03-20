package com.example.tbilisi_parking_final_exm.domain.usecase.parking.active_parking

import com.example.tbilisi_parking_final_exm.domain.repository.parking.active_parking.GetActiveParkingRepository
import javax.inject.Inject

class GetActiveParkingUseCase @Inject constructor(
    private val getActiveParkingRepository: GetActiveParkingRepository
) {
    suspend operator fun invoke(userId: Int) =
        getActiveParkingRepository.getActiveParking(userId = userId)
}