package com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.balance.toData
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance.AddToBalanceService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance.DeleteRememberedCardService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance.RememberCardService
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetAddBalanceRequest
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetCardDetails
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.balance.AddToBalanceRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class AddToBalanceRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val addToBalanceService: AddToBalanceService,
    private val deleteRememberedCardService: DeleteRememberedCardService,
    private val rememberCardService: RememberCardService,
//    private val rememberCardDataSource: RememberCardDataSource
) : AddToBalanceRepository {

//    override suspend fun rememberCard(getCardDetails: GetCardDetails): Flow<Resource<ResponseBody>> {
//        return handleResponse.safeApiCall {
//            rememberCardService.rememberCard(getCardDetails.toData())
//        }
//    }

    //    override suspend fun addToBalance(
//        addBalanceRequest: AddBalanceRequest,
//        getCardDetails: GetCardDetails,
//        isRememberCardChecked: Boolean
//    ): Flow<Resource<ResponseBody>> = flow {
//
//        emit(Resource.Loading(loading = true))
//
//        if (addBalanceRequest.cardId == null) {
//            val rememberCardResponse =
//                rememberCardDataSource.rememberCard(getCardDetails = getCardDetails)
//            try {
//                if (rememberCardResponse is Resource.Success) {
//                    val newAddBalanceRequest = addBalanceRequest.copy(
//                        cardId = rememberCardResponse.data.cardId
//                    )
//                    addToBalanceService.addToBalance(addBalanceRequest = newAddBalanceRequest)
//                    if (!isRememberCardChecked) {
//                        deleteRememberedCardService.deleteRememberedCard(newAddBalanceRequest.cardId)
//                    }
//                }
//            } catch (e: Throwable) {
//                emit(Resource.Loading(loading = false))
//                emit(Resource.Error(errorMessage = e.message ?: ""))
//            }
//        }
//
//        addToBalanceService.addToBalance(addBalanceRequest = addBalanceRequest)
//    }
    override suspend fun addToBalance(
        getAddBalanceRequest: GetAddBalanceRequest,
        getCardDetails: GetCardDetails,
        isRememberCardChecked: Boolean
    ): Flow<Resource<ResponseBody>> {
        if (getAddBalanceRequest.cardId != null)
            return handleResponse.safeApiCall {
                addToBalanceService.addToBalance(getAddBalanceRequest.toData())
            }

        return handleResponse.safeApiCall {
            val rememberCardResponse = rememberCardService.rememberCard(getCardDetails.toData())

            val cardId = rememberCardResponse.body()?.cardId ?: throw IllegalStateException()

            val newAddBalanceRequest = getAddBalanceRequest.copy(cardId = cardId)

            val addToBalanceResponse =
                addToBalanceService.addToBalance(newAddBalanceRequest.toData())

            if (!isRememberCardChecked)
                deleteRememberedCardService.deleteRememberedCard(cardId)

            addToBalanceResponse
        }
    }
}