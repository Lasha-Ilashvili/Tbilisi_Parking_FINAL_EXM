package com.example.tbilisi_parking_final_exm.presentation.event.transactions


sealed class TransactionsEvent {
    data class GetTransactions(val fromDate: String, val toDate: String) : TransactionsEvent()
}