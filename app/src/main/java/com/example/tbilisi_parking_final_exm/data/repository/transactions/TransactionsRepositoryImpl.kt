package com.example.tbilisi_parking_final_exm.data.repository.transactions

//import com.example.tbilisi_parking_final_exm.domain.repository.transactions.TransactionsRepository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.data_source.transactions.TransactionsPagingSource
import com.example.tbilisi_parking_final_exm.data.service.transactions.TransactionsService
import com.example.tbilisi_parking_final_exm.domain.model.transactions.GetTransaction
import com.example.tbilisi_parking_final_exm.domain.repository.transactions.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val transactionsService: TransactionsService
) : TransactionsRepository {

    override suspend fun getTransactions(
        userId: Int,
        fromDate: String,
        toDate: String
    ): Flow<PagingData<GetTransaction.GetTransactionItem>> {
        return Pager(PagingConfig(pageSize = 6)) {
            TransactionsPagingSource(
                service = transactionsService,
                userId = userId,
                fromDate = fromDate,
                toDate = toDate,
                pageSize = 6
            )
        }.flow
    }
}