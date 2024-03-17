package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetUserIdUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.main.GetRememberedCardsUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.main.WalletEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.main.toPresentation
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.main.WalletState
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
    private val getRememberedCardsUseCase: GetRememberedCardsUseCase
) : ViewModel() {

    private val _walletState = MutableStateFlow(WalletState())
    val walletState get() = _walletState.asStateFlow()

    fun onEvent(event: WalletEvent) = with(event) {
        when (this) {
            is WalletEvent.SetButtonState -> setButtonState(field = field)

            WalletEvent.GetRememberedCards -> getRememberedCards()

            WalletEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun getRememberedCards() {
        viewModelScope.launch {
            getRememberedCardsUseCase(getUserId()).collect {
                when (it) {
                    is Resource.Success -> _walletState.update { currentState ->
                        currentState.copy(data = it.data.map { getRememberedCard ->
                            getRememberedCard.toPresentation()
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