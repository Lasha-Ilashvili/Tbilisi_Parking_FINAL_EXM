package com.example.tbilisi_parking_final_exm.domain.repository.parking.get_vehicles

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.vehicle.GetVehicle
import kotlinx.coroutines.flow.Flow

interface GetAllVehicleRepository {
    suspend fun getAllVehicle(userId: Int) : Flow<Resource<List<GetVehicle>>>
}