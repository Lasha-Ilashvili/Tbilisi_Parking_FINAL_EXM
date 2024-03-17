package com.example.tbilisi_parking_final_exm.data.data_source.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.balance.toData
import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.balance.CardIdDto
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance.RememberCardService
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetCardDetails
import javax.inject.Inject

class RememberCardDataSource @Inject constructor(
    private val handleResponse: HandleResponse,
    private val rememberCardService: RememberCardService
) {

    suspend fun rememberCard(getCardDetails: GetCardDetails): Resource<CardIdDto> {
        return handleResponse.safeApiCallWithoutFlow {
            rememberCardService.rememberCard(getCardDetails.toData())
        }
    }
}