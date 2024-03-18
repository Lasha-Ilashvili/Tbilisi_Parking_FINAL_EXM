package com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.cards.GetUserCardsRepository
import javax.inject.Inject

class GetUserCardsUseCase @Inject constructor(
    private val getUserCardsRepository: GetUserCardsRepository
) {
    suspend operator fun invoke(cardId: Int) =
        getUserCardsRepository.getSavedCards(cardId)
}