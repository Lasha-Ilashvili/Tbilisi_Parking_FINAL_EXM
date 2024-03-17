package com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards.DeleteUserCardService
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.cards.DeleteCardsRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class DeleteCardsRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val deleteUserCardService: DeleteUserCardService
) : DeleteCardsRepository {

    override suspend fun deleteSavedCard(cardId: Int): Flow<Resource<ResponseBody>> {
        return handleResponse.safeApiCall {
            deleteUserCardService.deleteUserCard(cardId)
        }
    }
}