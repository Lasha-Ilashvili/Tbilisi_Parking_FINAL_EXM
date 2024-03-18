package com.example.tbilisi_parking_final_exm.domain.repository.cards

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.cards.GetCard
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    suspend fun getCards() : Flow<Resource<List<GetCard>>>
}