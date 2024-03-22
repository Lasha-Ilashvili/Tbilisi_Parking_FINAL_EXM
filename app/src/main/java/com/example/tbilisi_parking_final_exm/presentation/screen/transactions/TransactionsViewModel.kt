package com.example.tbilisi_parking_final_exm.presentation.screen.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetUserIdUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.transactions.GetTransactionsUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.transactions.TransactionsEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.transactions.toPresentation
import com.example.tbilisi_parking_final_exm.presentation.state.transactions.TransactionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getUserId: GetUserIdUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    private val _transactionsState = MutableStateFlow(TransactionsState())
    val transactionsState get() = _transactionsState.asStateFlow()

    fun onEvent(event: TransactionsEvent) = with(event) {
        when (this) {
            is TransactionsEvent.GetTransactions -> getCards(fromDate = fromDate, toDate = toDate)
            TransactionsEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun getCards(fromDate: String, toDate: String) {
        viewModelScope.launch {
            getTransactionsUseCase(
                userId = getUserId(),
                fromDate = fromDate,
                toDate = toDate
            ).collect {
                when (it) {
                    is Resource.Loading -> _transactionsState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> {
                        updateErrorMessage(message = it.errorMessage)
                    }

                    is Resource.Success -> _transactionsState.update { currentState ->
                        currentState.copy(data = it.data.map { getTransaction ->
                            getTransaction.toPresentation()
                        })
                    }
                }
            }
        }
    }

    private fun updateErrorMessage(message: String? = null) {
        _transactionsState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }
}