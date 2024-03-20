package com.example.tbilisi_parking_final_exm.data.repository.parking.active_parking

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.parking.active_parking.toDomain
import com.example.tbilisi_parking_final_exm.data.service.parking.active_parking.GetActiveParkingService
import com.example.tbilisi_parking_final_exm.domain.model.parking.active_parking.GetActiveParking
import com.example.tbilisi_parking_final_exm.domain.repository.parking.active_parking.GetActiveParkingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveParkingRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val getActiveParkingService: GetActiveParkingService
) : GetActiveParkingRepository {
    override suspend fun getActiveParking(userId: Int): Flow<Resource<List<GetActiveParking>>> {
        return handleResponse.safeApiCall {
            getActiveParkingService.getActiveParking(userId = userId)
        }.asResource {
            it.map {
                it.toDomain()
            }
        }
    }
}