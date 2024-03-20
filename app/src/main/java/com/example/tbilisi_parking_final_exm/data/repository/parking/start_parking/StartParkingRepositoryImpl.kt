package com.example.tbilisi_parking_final_exm.data.repository.parking.start_parking

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.parking.start_parking.toData
import com.example.tbilisi_parking_final_exm.data.mapper.parking.start_parking.toDomain
import com.example.tbilisi_parking_final_exm.data.service.parking.start_parking.StartParkingService
import com.example.tbilisi_parking_final_exm.domain.model.parking.start_parking.GetParkingIsStarted
import com.example.tbilisi_parking_final_exm.domain.model.parking.start_parking.GetStartParking
import com.example.tbilisi_parking_final_exm.domain.repository.parking.start_parking.StartParkingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartParkingRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val startParkingService: StartParkingService
) : StartParkingRepository {
    override suspend fun startParking(startParking: GetStartParking): Flow<Resource<GetParkingIsStarted>> {
        return handleResponse.safeApiCall {
            startParkingService.startParking(startParking = startParking.toData())
        }.asResource {
            it.toDomain()
        }
    }
}