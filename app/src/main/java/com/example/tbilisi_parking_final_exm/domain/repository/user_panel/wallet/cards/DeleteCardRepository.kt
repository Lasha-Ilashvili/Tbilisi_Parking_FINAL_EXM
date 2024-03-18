package com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface DeleteCardRepository {
    suspend fun deleteSavedCard(cardId: Int): Flow<Resource<Unit>>
}