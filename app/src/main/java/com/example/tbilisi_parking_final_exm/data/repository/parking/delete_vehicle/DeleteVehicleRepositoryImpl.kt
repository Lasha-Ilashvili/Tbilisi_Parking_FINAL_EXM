package com.example.tbilisi_parking_final_exm.data.repository.parking.delete_vehicle

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.parking.vehicle.toDomain
import com.example.tbilisi_parking_final_exm.data.service.parking.delete_vehicle.DeleteVehicleService
import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.vehicle.GetVehicle
import com.example.tbilisi_parking_final_exm.domain.repository.parking.delete_vehicle.DeleteVehicleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteVehicleRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val deleteVehicleService: DeleteVehicleService
) : DeleteVehicleRepository {
    override suspend fun deleteVehicle(vehicleId: Int): Flow<Resource<GetVehicle>> {
        return handleResponse.safeApiCall {
            deleteVehicleService.deleteVehicle(vehicleId = vehicleId)
        }.asResource {
            it.toDomain()
        }
    }
}