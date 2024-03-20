package com.example.tbilisi_parking_final_exm.domain.usecase.parking.start_parking

import com.example.tbilisi_parking_final_exm.domain.model.parking.start_parking.GetStartParking
import com.example.tbilisi_parking_final_exm.domain.repository.parking.start_parking.StartParkingRepository
import javax.inject.Inject

class StartParkingUseCase @Inject constructor(
    private val startParkingRepository: StartParkingRepository
) {
    suspend operator fun invoke(startParking: GetStartParking) =
        startParkingRepository.startParking(startParking = startParking)
}