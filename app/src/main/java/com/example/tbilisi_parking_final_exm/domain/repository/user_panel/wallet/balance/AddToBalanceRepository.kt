package com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetAddBalanceRequest
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.cards.GetCardDetails
import kotlinx.coroutines.flow.Flow

interface AddToBalanceRepository {
    suspend fun addToBalance(
        getAddBalanceRequest: GetAddBalanceRequest,
        getCardDetails: GetCardDetails,
        isRememberCardChecked: Boolean
    ): Flow<Resource<Unit>>
}