package com.example.tbilisi_parking_final_exm.domain.repository.parking.add_vehicle

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.add_vehicle.GetAddVehicle
import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.vehicle.GetVehicle
import kotlinx.coroutines.flow.Flow

interface AddVehicleRepository {
    suspend fun addVehicle(vehicle: GetAddVehicle) : Flow<Resource<GetVehicle>>
}