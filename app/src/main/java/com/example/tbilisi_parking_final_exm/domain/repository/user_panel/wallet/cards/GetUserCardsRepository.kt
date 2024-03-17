package com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.cards.GetUserCard
import kotlinx.coroutines.flow.Flow

interface GetUserCardsRepository {
    suspend fun getSavedCards(userId: Int): Flow<Resource<List<GetUserCard>>>
}