package com.example.tbilisi_parking_final_exm.data.repository.licenses

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.licenses.toDomain
import com.example.tbilisi_parking_final_exm.data.service.licenses.LicensesService
import com.example.tbilisi_parking_final_exm.domain.model.licenses.GetLicense
import com.example.tbilisi_parking_final_exm.domain.repository.licenses.LicensesRepository
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