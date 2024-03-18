package com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.cards.DeleteCardRepository
import javax.inject.Inject

class DeleteCardUseCase @Inject constructor(
    private val deleteCardRepository: DeleteCardRepository
) {
    suspend operator fun invoke(cardId: Int) =
        deleteCardRepository.deleteSavedCard(cardId)
}