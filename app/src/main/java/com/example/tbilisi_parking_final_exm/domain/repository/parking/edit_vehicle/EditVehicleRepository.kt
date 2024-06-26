package com.example.tbilisi_parking_final_exm.domain.repository.parking.edit_vehicle

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.edit_vehicle.GetEditVehicle
import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.vehicle.GetVehicle
import kotlinx.coroutines.flow.Flow

interface EditVehicleRepository {

    suspend fun editVehicle(vehicle: GetEditVehicle) : Flow<Resource<GetVehicle>>
}