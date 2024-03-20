package com.example.tbilisi_parking_final_exm.domain.repository.parking.delete_vehicle

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.vehicle.GetVehicle
import kotlinx.coroutines.flow.Flow

interface DeleteVehicleRepository {
    suspend fun deleteVehicle(vehicleId: Int): Flow<Resource<GetVehicle>>
}