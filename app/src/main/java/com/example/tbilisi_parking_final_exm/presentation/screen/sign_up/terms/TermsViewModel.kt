package com.example.tbilisi_parking_final_exm.presentation.screen.sign_up.terms

import androidx.lifecycle.ViewModel
import com.example.tbilisi_parking_final_exm.presentation.event.sign_up.terms.TermsEvent
import com.example.tbilisi_parking_final_exm.presentation.state.sign_up.terms.TermsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TermsViewModel : ViewModel() {

    private val _termsState = MutableStateFlow(TermsState())
    val termsState get() = _termsState.asStateFlow()

    fun onEvent(event: TermsEvent) {
        when (event) {
            is TermsEvent.SetButtonState -> setButtonState(event.isChecked)
        }
    }

    private fun setButtonState(isChecked: Boolean) {
        _termsState.update { currentState ->
            currentState.copy(isEnabled = isChecked)
        }
    }
}