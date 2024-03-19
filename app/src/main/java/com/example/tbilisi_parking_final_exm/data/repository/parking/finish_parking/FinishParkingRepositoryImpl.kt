package com.example.tbilisi_parking_final_exm.data.repository.parking.finish_parking

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.parking.finish_parking.toData
import com.example.tbilisi_parking_final_exm.data.mapper.parking.finish_parking.toDomain
import com.example.tbilisi_parking_final_exm.data.service.parking.finish_parking.FinishParkingService
import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetFinishParking
import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetParkingIsFinished
import com.example.tbilisi_parking_final_exm.domain.repository.parking.finish_parking.FinishParkingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FinishParkingRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val finishParkingService: FinishParkingService
) : FinishParkingRepository {
    override suspend fun finishParking(finishParking: GetFinishParking): Flow<Resource<GetParkingIsFinished>> {
        return handleResponse.safeApiCall {
            finishParkingService.finishParking(finishParking = finishParking.toData())
        }.asResource {
            it.toDomain()
        }
    }
}