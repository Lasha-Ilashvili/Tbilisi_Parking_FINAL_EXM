package com.example.tbilisi_parking_final_exm.domain.repository.parking.active_licenses

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.active_licenses.GetActiveLicense
import kotlinx.coroutines.flow.Flow


interface ActiveLicensesRepository {
    suspend fun getActiveLicenses(carId:Int): Flow<Resource<List<GetActiveLicense>>>
}