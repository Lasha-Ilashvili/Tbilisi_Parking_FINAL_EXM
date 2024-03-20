package com.example.tbilisi_parking_final_exm.presentation.state.transactions

import com.example.tbilisi_parking_final_exm.presentation.model.transactions.Transaction


data class TransactionsState(
    val isLoading: Boolean = false,
    val data: List<Transaction>? = null,
    val errorMessage: String? = null
)