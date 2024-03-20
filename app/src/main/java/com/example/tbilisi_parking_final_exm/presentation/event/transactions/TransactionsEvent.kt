package com.example.tbilisi_parking_final_exm.presentation.event.transactions


sealed class TransactionsEvent {
    data object GetTransactions : TransactionsEvent()
    data object ResetErrorMessage : TransactionsEvent()
}