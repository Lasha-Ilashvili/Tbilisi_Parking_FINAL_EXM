package com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.main

import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.main.RememberedCardsRepository
import javax.inject.Inject

class GetRememberedCardsUseCase @Inject constructor(
    private val rememberedCardsRepository: RememberedCardsRepository
) {
    suspend operator fun invoke(userId: Int) =
        rememberedCardsRepository.getRememberedCards(userId)
}