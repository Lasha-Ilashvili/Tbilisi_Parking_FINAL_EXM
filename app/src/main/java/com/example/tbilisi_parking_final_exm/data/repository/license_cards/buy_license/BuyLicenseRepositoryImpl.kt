package com.example.tbilisi_parking_final_exm.data.repository.license_cards.buy_license

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.license_cards.buy_license.toData
import com.example.tbilisi_parking_final_exm.data.service.license_cards.buy_license.BuyLicenseService
import com.example.tbilisi_parking_final_exm.domain.model.license_cards.buy_license.GetBuyLicenseRequest
import com.example.tbilisi_parking_final_exm.domain.repository.license_cards.buy_license.BuyLicenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BuyLicenseRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val buyLicenseService: BuyLicenseService
) : BuyLicenseRepository {

    override suspend fun buyLicense(getBuyLicenseRequest: GetBuyLicenseRequest): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            buyLicenseService.buyLicense(buyLicenseRequestDto = getBuyLicenseRequest.toData())
        }
    }
}