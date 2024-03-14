package com.example.tbilisi_parking_final_exm.domain.usecase.vehicle.get_vehicles

import com.example.tbilisi_parking_final_exm.domain.repository.get_vehicles.GetAllVehicleRepository
import javax.inject.Inject

class GetAllVehicleUseCase @Inject constructor(
    private val getAllVehicleRepository: GetAllVehicleRepository
) {
    suspend operator fun invoke(userId: Int) = getAllVehicleRepository.getAllVehicle(userId = userId)
}