package com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetBalance
import kotlinx.coroutines.flow.Flow

interface GetBalanceRepository {

    suspend fun getBalance(userId: Int): Flow<Resource<GetBalance>>
}