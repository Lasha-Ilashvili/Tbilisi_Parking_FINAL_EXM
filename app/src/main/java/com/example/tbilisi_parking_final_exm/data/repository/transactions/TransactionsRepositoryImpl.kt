package com.example.tbilisi_parking_final_exm.data.repository.transactions

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.transactions.toDomain
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
    ): Flow<Resource<List<GetTransaction>>> {
        return handleResponse.safeApiCall {
            transactionsService.getTransactions(
                userId = userId,
                fromDate = fromDate,
                toDate = toDate
            )
        }.asResource {
            it.map { dto ->
                dto.toDomain()
            }
        }
    }
}