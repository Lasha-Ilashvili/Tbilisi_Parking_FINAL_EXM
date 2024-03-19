package com.example.tbilisi_parking_final_exm.domain.repository.license_cards.buy_license

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.license_cards.buy_license.GetBuyLicenseRequest
import kotlinx.coroutines.flow.Flow

interface BuyLicenseRepository {
    suspend fun buyLicense(getBuyLicenseRequest: GetBuyLicenseRequest): Flow<Resource<Unit>>
}