package com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.main

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.main.GetRememberedCard
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface RememberedCardsRepository {
    suspend fun getRememberedCards(userId: Int): Flow<Resource<List<GetRememberedCard>>>
    suspend fun deleteRememberedCard(cardId: Int): Flow<Resource<ResponseBody>>
}