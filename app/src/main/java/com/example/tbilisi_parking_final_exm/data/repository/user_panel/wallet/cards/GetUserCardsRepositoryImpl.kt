package com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.cards.toDomain
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards.GetUserCardsService
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.cards.GetUserCard
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.cards.GetUserCardsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserCardsRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val getUserCardsService: GetUserCardsService
) : GetUserCardsRepository {

    override suspend fun getSavedCards(userId: Int): Flow<Resource<List<GetUserCard>>> {
        return handleResponse.safeApiCall {
            getUserCardsService.getUserCards(userId)
        }.asResource {
            it.map { dto ->
                dto.toDomain()
            }
        }
    }
}