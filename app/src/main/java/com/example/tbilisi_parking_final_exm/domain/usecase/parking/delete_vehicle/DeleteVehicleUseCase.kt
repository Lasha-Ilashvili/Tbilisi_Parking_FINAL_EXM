package com.example.tbilisi_parking_final_exm.domain.usecase.parking.delete_vehicle

import com.example.tbilisi_parking_final_exm.domain.repository.parking.delete_vehicle.DeleteVehicleRepository
import javax.inject.Inject

class DeleteVehicleUseCase @Inject constructor(
    private val deleteVehicleRepository: DeleteVehicleRepository
) {

    suspend operator fun invoke(vehicleId: Int) =
        deleteVehicleRepository.deleteVehicle(vehicleId = vehicleId)
}