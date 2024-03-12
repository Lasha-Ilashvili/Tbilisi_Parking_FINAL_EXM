package com.example.tbilisi_parking_final_exm.domain.usecase.add_vehicle

import com.example.tbilisi_parking_final_exm.domain.model.vehicle.add_vehicle.GetAddVehicle
import com.example.tbilisi_parking_final_exm.domain.repository.add_vehicle.AddVehicleRepository
import javax.inject.Inject

class AddVehicleUseCase @Inject constructor(
    private val addVehicleRepository: AddVehicleRepository
) {
    suspend operator fun invoke(addVehicle: GetAddVehicle) = addVehicleRepository.addVehicle(vehicle =addVehicle)
}