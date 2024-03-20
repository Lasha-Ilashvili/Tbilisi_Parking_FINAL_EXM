package com.example.tbilisi_parking_final_exm.domain.repository.parking.start_parking

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.parking.start_parking.GetParkingIsStarted
import com.example.tbilisi_parking_final_exm.domain.model.parking.start_parking.GetStartParking
import kotlinx.coroutines.flow.Flow

interface StartParkingRepository {

    suspend fun startParking( startParking: GetStartParking): Flow<Resource<GetParkingIsStarted>>
}