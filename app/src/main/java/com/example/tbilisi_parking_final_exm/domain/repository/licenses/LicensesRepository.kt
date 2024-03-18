package com.example.tbilisi_parking_final_exm.domain.repository.licenses

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.licenses.GetLicense
import kotlinx.coroutines.flow.Flow

interface LicensesRepository {
    suspend fun getLicenses() : Flow<Resource<List<GetLicense>>>
}