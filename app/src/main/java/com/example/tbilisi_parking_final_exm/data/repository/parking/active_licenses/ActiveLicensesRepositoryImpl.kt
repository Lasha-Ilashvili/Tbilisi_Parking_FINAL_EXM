package com.example.tbilisi_parking_final_exm.data.repository.parking.active_licenses

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.active_licenses.toDomain
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.service.parking.active_licenses.ActiveLicensesService
import com.example.tbilisi_parking_final_exm.domain.model.active_licenses.GetActiveLicense
import com.example.tbilisi_parking_final_exm.domain.repository.parking.active_licenses.ActiveLicensesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActiveLicensesRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val activeLicensesService: ActiveLicensesService
) : ActiveLicensesRepository {

    override suspend fun getActiveLicenses(carId: Int): Flow<Resource<List<GetActiveLicense>>> {
        return handleResponse.safeApiCall {
            activeLicensesService.getActiveLicenses(carId = carId)
        }.asResource {
            it.map { dto ->
                dto.toDomain()
            }
        }
    }
}