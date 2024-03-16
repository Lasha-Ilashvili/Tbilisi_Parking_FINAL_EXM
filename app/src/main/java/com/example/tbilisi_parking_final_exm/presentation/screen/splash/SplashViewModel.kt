package com.example.tbilisi_parking_final_exm.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetSessionUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.profile.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase,
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUiEvent>()
    val uiEvent get() = _uiEvent

    init {
        readSession()
    }

    private fun readSession() {
        viewModelScope.launch {
            getSessionUseCase.invoke().collect {
                if (it.isEmpty()) {
                    _uiEvent.emit(SplashUiEvent.NavigateToHomeFragment)
                } else {
                    getProfileUseCase.invoke().collect {
                        when (it) {
                            is Resource.Success ->{
                                _uiEvent.emit(SplashUiEvent.NavigateToBottomNavFragment)
                            }

                            is Resource.Loading -> {}

                            is Resource.Error -> _uiEvent.emit(SplashUiEvent.NavigateToHomeFragment)
                        }
                    }
                }
            }
        }
    }

    sealed interface SplashUiEvent {
        data object NavigateToBottomNavFragment : SplashUiEvent
        data object NavigateToHomeFragment : SplashUiEvent
    }
}