package com.example.tbilisi_parking_final_exm.domain.repository.parking.active_parking

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.parking.active_parking.GetActiveParking
import kotlinx.coroutines.flow.Flow

interface GetActiveParkingRepository {
    suspend fun getActiveParking(userId: Int): Flow<Resource<List<GetActiveParking>>>
}