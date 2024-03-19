package com.example.tbilisi_parking_final_exm.domain.repository.license_cards.all_licenses

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.license_cards.all_license.GetLicense
import kotlinx.coroutines.flow.Flow

interface LicensesRepository {
    suspend fun getLicenses() : Flow<Resource<List<GetLicense>>>
}