package com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards.DeleteUserCardService
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.cards.DeleteCardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCardRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val deleteUserCardService: DeleteUserCardService
) : DeleteCardRepository {

    override suspend fun deleteSavedCard(cardId: Int): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            deleteUserCardService.deleteUserCard(cardId)
        }
    }
}