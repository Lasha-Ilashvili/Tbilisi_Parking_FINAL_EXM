package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetUserIdUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.balance.GetBalanceUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.cards.DeleteCardUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.cards.GetUserCardsUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.WalletEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.cards.toPresentation
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.WalletState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WalletViewModel @Inject constructor(
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val getUserId: GetUserIdUseCase,
    private val getUserCardsUseCase: GetUserCardsUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val deleteCardsUseCase: DeleteCardUseCase
) : ViewModel() {

    private val _walletState = MutableStateFlow(WalletState())
    val walletState get() = _walletState.asStateFlow()

    fun onEvent(event: WalletEvent) = with(event) {
        when (this) {
            is WalletEvent.SetButtonState -> setButtonState(field = field)

            is WalletEvent.DeleteCard -> deleteCard(cardId = cardId)

            WalletEvent.GetUserCards -> getSavedCards()

            WalletEvent.GetBalance -> getBalance()

            WalletEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun getSavedCards() {
        viewModelScope.launch {
            getUserCardsUseCase(getUserId()).collect {
                when (it) {
                    is Resource.Success -> _walletState.update { currentState ->
                        currentState.copy(data = it.data.map { getSavedCard ->
                            getSavedCard.toPresentation()
                        })
                    }

                    is Resource.Loading -> _walletState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun getBalance() {
        viewModelScope.launch {
            getBalanceUseCase(getUserId()).collect {
                when (it) {
                    is Resource.Success -> _walletState.update { currentState ->
                        currentState.copy(balance = it.data.toPresentation())
                    }

                    is Resource.Loading -> _walletState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun deleteCard(cardId: Int) {
        viewModelScope.launch {
            deleteCardsUseCase(cardId = cardId).collect {
                when (it) {
                    is Resource.Success -> getSavedCards()

                    is Resource.Loading -> _walletState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun setButtonState(field: TextInputLayout) {
        _walletState.update { currentState ->
            currentState.copy(isButtonEnabled = fieldsAreNotBlank(listOf(field)))
        }
    }

    private fun updateErrorMessage(message: String? = null) {
        _walletState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }
}