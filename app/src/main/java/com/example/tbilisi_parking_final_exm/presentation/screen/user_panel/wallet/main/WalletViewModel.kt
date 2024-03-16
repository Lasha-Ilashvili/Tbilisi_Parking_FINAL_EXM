package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet.main

import androidx.lifecycle.ViewModel
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.main.WalletEvent
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.main.WalletState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class WalletViewModel @Inject constructor(
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase
) : ViewModel() {

    private val _walletState = MutableStateFlow(WalletState())
    val walletState get() = _walletState.asStateFlow()

//    private val _uiEvent = MutableSharedFlow<WalletUiEvent>()
//    val uiEvent get() = _uiEvent.asSharedFlow()

    fun onEvent(event: WalletEvent) = with(event) {
        when (this) {
            is WalletEvent.SetButtonState -> setButtonState(field = field)
        }
    }

    private fun setButtonState(field: TextInputLayout) {
        _walletState.update { currentState ->
            currentState.copy(isButtonEnabled = fieldsAreNotBlank(listOf(field)))
        }
    }

    //    sealed interface WalletUiEvent {
//        data class NavigateToBalance(
//            val cardNumber: String,
//            val date: String,
//            val cvv: String
//        ) : WalletUiEvent
//    }
}