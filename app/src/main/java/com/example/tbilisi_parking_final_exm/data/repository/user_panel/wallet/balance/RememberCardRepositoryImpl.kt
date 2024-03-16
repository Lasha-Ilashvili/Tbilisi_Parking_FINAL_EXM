package com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.balance.toData
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance.RememberCardService
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetCardDetails
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.balance.RememberCardRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class RememberCardRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val rememberCardService: RememberCardService
) : RememberCardRepository {

    override suspend fun rememberCard(getCardDetails: GetCardDetails): Flow<Resource<ResponseBody>> {
        return handleResponse.safeApiCall {
            rememberCardService.rememberCard(getCardDetails.toData())
        }
    }
}