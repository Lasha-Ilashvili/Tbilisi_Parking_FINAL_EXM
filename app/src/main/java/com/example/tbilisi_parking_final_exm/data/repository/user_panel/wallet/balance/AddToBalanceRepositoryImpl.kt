package com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.extension.parseErrorMessage
import com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.balance.toData
import com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.cards.toData
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance.AddToBalanceService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards.DeleteUserCardService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards.SaveCardService
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetAddBalanceRequest
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.cards.GetCardDetails
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.balance.AddToBalanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToBalanceRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val addToBalanceService: AddToBalanceService,
    private val deleteUserCardService: DeleteUserCardService,
    private val saveCardService: SaveCardService
) : AddToBalanceRepository {

    override suspend fun addToBalance(
        getAddBalanceRequest: GetAddBalanceRequest,
        getCardDetails: GetCardDetails,
        isRememberCardChecked: Boolean
    ): Flow<Resource<Unit>> {
        if (getAddBalanceRequest.cardId != null)
            return handleResponse.safeApiCall {
                addToBalanceService.addToBalance(getAddBalanceRequest.toData())
            }

        return handleResponse.safeApiCall {
            val rememberCardResponse = saveCardService.saveCard(getCardDetails.toData())

            if (!rememberCardResponse.isSuccessful) {
                val errorBody = rememberCardResponse.errorBody().parseErrorMessage()
                throw Exception(errorBody)
            }

            val cardId = rememberCardResponse.body()?.cardId!!

            val newAddBalanceRequest = getAddBalanceRequest.copy(cardId = cardId)

            val addToBalanceResponse =
                addToBalanceService.addToBalance(newAddBalanceRequest.toData())

            if (!isRememberCardChecked)
                deleteUserCardService.deleteUserCard(cardId)

            addToBalanceResponse
        }
    }
}