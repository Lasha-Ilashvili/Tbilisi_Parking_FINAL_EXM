package com.example.tbilisi_parking_final_exm.data.repository.parking.add_vehicle

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.vehicle.toData
import com.example.tbilisi_parking_final_exm.data.mapper.vehicle.toDomain
import com.example.tbilisi_parking_final_exm.data.service.parking.add_vehicle.AddVehicleService
import com.example.tbilisi_parking_final_exm.domain.model.vehicle.add_vehicle.GetAddVehicle
import com.example.tbilisi_parking_final_exm.domain.model.vehicle.vehicle.GetVehicle
import com.example.tbilisi_parking_final_exm.domain.repository.parking.add_vehicle.AddVehicleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddVehicleRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val addVehicleService: AddVehicleService
) : AddVehicleRepository {
    override suspend fun addVehicle(vehicle: GetAddVehicle): Flow<Resource<GetVehicle>> {
        return handleResponse.safeApiCall {
            addVehicleService.addVehicle(vehicle.toData()).apply {
            }
        }.asResource {
            it.toDomain()
        }
    }
}