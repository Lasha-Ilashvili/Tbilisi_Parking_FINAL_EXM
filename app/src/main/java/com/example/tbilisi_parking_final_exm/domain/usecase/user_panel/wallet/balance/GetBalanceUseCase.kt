package com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.cards.GetBalanceRepository
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val getBalanceRepository: GetBalanceRepository
) {
    suspend operator fun invoke(userId: Int) =
        getBalanceRepository.getBalance(userId)
}