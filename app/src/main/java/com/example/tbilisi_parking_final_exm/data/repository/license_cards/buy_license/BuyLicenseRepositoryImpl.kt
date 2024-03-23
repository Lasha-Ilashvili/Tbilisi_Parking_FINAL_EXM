package com.example.tbilisi_parking_final_exm.data.repository.license_cards.buy_license

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.extension.parseErrorMessage
import com.example.tbilisi_parking_final_exm.data.mapper.license_cards.buy_license.toData
import com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.cards.toData
import com.example.tbilisi_parking_final_exm.data.service.license_cards.buy_license.BuyLicenseService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards.DeleteUserCardService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards.SaveCardService
import com.example.tbilisi_parking_final_exm.domain.model.license_cards.buy_license.GetBuyLicenseRequest
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.cards.GetCardDetails
import com.example.tbilisi_parking_final_exm.domain.repository.license_cards.buy_license.BuyLicenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BuyLicenseRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val buyLicenseService: BuyLicenseService,
    private val deleteUserCardService: DeleteUserCardService,
    private val saveCardService: SaveCardService
) : BuyLicenseRepository {

    override suspend fun buyLicense(
        getBuyLicenseRequest: GetBuyLicenseRequest,
        getCardDetails: GetCardDetails?
    ): Flow<Resource<Unit>> {
        if (getCardDetails == null)
            return handleResponse.safeApiCall {
                buyLicenseService.buyLicense(buyLicenseRequestDto = getBuyLicenseRequest.toData())
            }

        return handleResponse.safeApiCall {
            val rememberCardResponse = getCardDetails.toData().let {
                saveCardService.saveCard(it)
            }

            if (!rememberCardResponse.isSuccessful) {
                val errorBody = rememberCardResponse.errorBody().parseErrorMessage()
                throw Exception(errorBody)
            }

            val cardId = rememberCardResponse.body()?.cardId ?: throw IllegalStateException()

            val buyLicenseRequestDto = getBuyLicenseRequest.toData()

            val buyLicenseResponse =
                buyLicenseService.buyLicense(buyLicenseRequestDto = buyLicenseRequestDto.copy(cardId = cardId))

            deleteUserCardService.deleteUserCard(cardId)

            buyLicenseResponse
        }
    }
}