package com.example.tbilisi_parking_final_exm.domain.usecase.edit_vehicle

import com.example.tbilisi_parking_final_exm.domain.model.vehicle.edit_vehicle.GetEditVehicle
import com.example.tbilisi_parking_final_exm.domain.repository.edit_vehicle.EditVehicleRepository
import javax.inject.Inject

class EditVehicleUseCase @Inject constructor(
    private val editVehicleRepository: EditVehicleRepository
) {
    suspend operator fun invoke(vehicle: GetEditVehicle) = editVehicleRepository.editVehicle(vehicle = vehicle)
}