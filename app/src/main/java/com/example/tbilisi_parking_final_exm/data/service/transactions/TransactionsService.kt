package com.example.tbilisi_parking_final_exm.data.service.transactions

import com.example.tbilisi_parking_final_exm.data.model.transactions.TransactionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface TransactionsService {
    @GET("transaction/history")
    suspend fun getTransactions(
        @Query("userId") userId: Int,
        @Query("fromDate") fromDate: String,
        @Query("toDate") toDate: String,
        @Query("page") page: Int,
        @Query("size") pageSize: Int
    ): Response<TransactionDto>
}