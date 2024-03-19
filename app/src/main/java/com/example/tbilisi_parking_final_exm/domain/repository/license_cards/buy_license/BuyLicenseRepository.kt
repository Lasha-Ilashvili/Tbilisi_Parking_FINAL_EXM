package com.example.tbilisi_parking_final_exm.domain.repository.license_cards.buy_license

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.license_cards.buy_license.GetBuyLicenseRequest
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.cards.GetCardDetails
import kotlinx.coroutines.flow.Flow

interface BuyLicenseRepository {
    suspend fun buyLicense(
        getBuyLicenseRequest: GetBuyLicenseRequest,
        getCardDetails: GetCardDetails?
    ): Flow<Resource<Unit>>
}