package com.example.tbilisi_parking_final_exm.presentation.screen.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

class SplashViewModel: ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUiEvent>()
    val uiEvent get() = _uiEvent

    init {
        readSession()
    }

    private fun readSession() {

    }


    sealed interface SplashUiEvent {
        data object NavigateToBottomNavFragment : SplashUiEvent
        data object NavigateToHomeFragment: SplashUiEvent
    }
}