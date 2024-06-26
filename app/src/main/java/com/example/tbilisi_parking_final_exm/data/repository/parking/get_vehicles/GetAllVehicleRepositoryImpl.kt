package com.example.tbilisi_parking_final_exm.data.repository.parking.get_vehicles

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.parking.vehicle.toDomain
import com.example.tbilisi_parking_final_exm.data.service.parking.get_vehicle.GetAllVehicleService
import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.vehicle.GetVehicle
import com.example.tbilisi_parking_final_exm.domain.repository.parking.get_vehicles.GetAllVehicleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllVehicleRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val getAllVehicleService: GetAllVehicleService
) : GetAllVehicleRepository {
    override suspend fun getAllVehicle(userId: Int): Flow<Resource<List<GetVehicle>>> {
        return handleResponse.safeApiCall {
            getAllVehicleService.getAllVehicle(userId = userId)
        }.asResource {
            it.map {
                it.toDomain()
            }
        }
    }
}