package com.example.tbilisi_parking_final_exm.domain.usecase.parking.finish_parking

import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetFinishParking
import com.example.tbilisi_parking_final_exm.domain.repository.parking.finish_parking.FinishParkingRepository
import javax.inject.Inject

class FinishParkingUseCase @Inject constructor(
    private val finishParkingRepository: FinishParkingRepository
) {
    suspend operator fun invoke(finishParking: GetFinishParking) =
        finishParkingRepository.finishParking(finishParking = finishParking)
}