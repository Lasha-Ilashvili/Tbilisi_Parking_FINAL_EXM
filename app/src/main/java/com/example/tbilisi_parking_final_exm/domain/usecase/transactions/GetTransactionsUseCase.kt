package com.example.tbilisi_parking_final_exm.domain.usecase.transactions

import com.example.tbilisi_parking_final_exm.domain.repository.transactions.TransactionsRepository
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {

    suspend operator fun invoke(userId: Int, fromDate: String, toDate: String) =
        transactionsRepository.getTransactions(
            userId = userId,
            fromDate = fromDate,
            toDate = toDate
        )
}