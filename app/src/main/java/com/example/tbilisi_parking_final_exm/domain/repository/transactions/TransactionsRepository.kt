package com.example.tbilisi_parking_final_exm.domain.repository.transactions

import androidx.paging.PagingData
import com.example.tbilisi_parking_final_exm.domain.model.transactions.GetTransaction
import kotlinx.coroutines.flow.Flow


interface TransactionsRepository {
    suspend fun getTransactions(
        userId: Int,
        fromDate: String,
        toDate: String
    ): Flow<PagingData<GetTransaction.GetTransactionItem>>
}