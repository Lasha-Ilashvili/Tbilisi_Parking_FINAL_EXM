package com.example.tbilisi_parking_final_exm.domain.repository.parking.finish_parking

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetFinishParking
import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetParkingIsFinished
import kotlinx.coroutines.flow.Flow

interface FinishParkingRepository {
    suspend fun finishParking(finishParking: GetFinishParking): Flow<Resource<GetParkingIsFinished>>
}