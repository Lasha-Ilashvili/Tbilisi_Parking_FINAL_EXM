package com.example.tbilisi_parking_final_exm.data.repository.license_cards.all_license

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.license_cards.all_licenses.toDomain
import com.example.tbilisi_parking_final_exm.data.service.license_cards.all_licenses.LicensesService
import com.example.tbilisi_parking_final_exm.domain.model.license_cards.all_license.GetLicense
import com.example.tbilisi_parking_final_exm.domain.repository.license_cards.all_licenses.LicensesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LicensesRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val licensesService: LicensesService
) : LicensesRepository {

    override suspend fun getLicenses(): Flow<Resource<List<GetLicense>>> {
        return handleResponse.safeApiCall {
            licensesService.getLicenses()
        }.asResource {
            it.map {dto->
                dto.toDomain()
            }
        }
    }
}