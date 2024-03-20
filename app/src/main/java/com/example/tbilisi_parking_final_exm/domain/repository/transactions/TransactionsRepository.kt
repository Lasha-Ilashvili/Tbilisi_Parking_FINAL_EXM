package com.example.tbilisi_parking_final_exm.domain.repository.transactions

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.transactions.GetTransaction
import kotlinx.coroutines.flow.Flow


interface TransactionsRepository {
    suspend fun getTransactions(
        userId: Int,
        fromDate: String,
        toDate: String
    ): Flow<Resource<List<GetTransaction>>>
}