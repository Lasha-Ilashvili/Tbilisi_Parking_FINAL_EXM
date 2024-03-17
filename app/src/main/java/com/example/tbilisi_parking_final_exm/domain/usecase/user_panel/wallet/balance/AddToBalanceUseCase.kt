package com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetAddBalanceRequest
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.cards.GetCardDetails
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.balance.AddToBalanceRepository
import javax.inject.Inject

class AddToBalanceUseCase @Inject constructor(
    private val addToBalanceRepository: AddToBalanceRepository
) {
    suspend operator fun invoke(
        getAddBalanceRequest: GetAddBalanceRequest,
        getCardDetails: GetCardDetails,
        isRememberCardChecked: Boolean
    ) =
        addToBalanceRepository.addToBalance(
            getAddBalanceRequest = getAddBalanceRequest,
            getCardDetails = getCardDetails,
            isRememberCardChecked = isRememberCardChecked
        )
}