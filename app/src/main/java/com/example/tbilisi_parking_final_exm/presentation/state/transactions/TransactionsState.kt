package com.example.tbilisi_parking_final_exm.presentation.state.transactions

import androidx.paging.PagingData
import com.example.tbilisi_parking_final_exm.presentation.model.transactions.Transaction


data class TransactionsState(
    val isLoading: Boolean = false,
    val data: PagingData<Transaction.TransactionItem>? = null,
    val errorMessage: String? = null
)