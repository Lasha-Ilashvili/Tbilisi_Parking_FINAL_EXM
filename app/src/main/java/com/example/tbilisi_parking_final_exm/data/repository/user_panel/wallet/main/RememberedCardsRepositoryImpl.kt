package com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.main

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.main.toDomain
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance.DeleteRememberedCardService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.main.RememberedCardsService
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.main.GetRememberedCard
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.main.RememberedCardsRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class RememberedCardsRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val rememberedCardsService: RememberedCardsService,
    private val deleteRememberedCardService: DeleteRememberedCardService
) : RememberedCardsRepository {

    override suspend fun getRememberedCards(userId: Int): Flow<Resource<List<GetRememberedCard>>> {
        return handleResponse.safeApiCall {
            rememberedCardsService.getRememberedCards(userId)
        }.asResource {
            it.map { dto ->
                dto.toDomain()
            }
        }
    }

    override suspend fun deleteRememberedCard(cardId: Int): Flow<Resource<ResponseBody>> {
        return handleResponse.safeApiCall {
            deleteRememberedCardService.deleteRememberedCard(cardId)
        }
    }
}