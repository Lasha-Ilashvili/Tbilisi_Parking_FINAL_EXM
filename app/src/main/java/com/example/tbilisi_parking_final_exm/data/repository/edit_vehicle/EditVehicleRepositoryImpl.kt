package com.example.tbilisi_parking_final_exm.data.repository.edit_vehicle

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.vehicle.toData
import com.example.tbilisi_parking_final_exm.data.mapper.vehicle.toDomain
import com.example.tbilisi_parking_final_exm.data.service.edit_vehicle.EditVehicleService
import com.example.tbilisi_parking_final_exm.domain.model.vehicle.edit_vehicle.GetEditVehicle
import com.example.tbilisi_parking_final_exm.domain.model.vehicle.vehicle.GetVehicle
import com.example.tbilisi_parking_final_exm.domain.repository.edit_vehicle.EditVehicleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditVehicleRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val editVehicleService: EditVehicleService
) : EditVehicleRepository {
    override suspend fun editVehicle(vehicle: GetEditVehicle): Flow<Resource<GetVehicle>> {
        return handleResponse.safeApiCall {
            editVehicleService.editVehicle(vehicle = vehicle.toData())
        }.asResource {
            it.toDomain()
        }
    }
}