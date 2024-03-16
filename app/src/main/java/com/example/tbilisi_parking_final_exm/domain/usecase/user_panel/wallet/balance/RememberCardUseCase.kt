package com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetCardDetails
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.balance.RememberCardRepository
import javax.inject.Inject

class RememberCardUseCase @Inject constructor(
    private val rememberCardRepository: RememberCardRepository
) {
    suspend operator fun invoke(getCardDetails: GetCardDetails) =
        rememberCardRepository.rememberCard(getCardDetails)
}