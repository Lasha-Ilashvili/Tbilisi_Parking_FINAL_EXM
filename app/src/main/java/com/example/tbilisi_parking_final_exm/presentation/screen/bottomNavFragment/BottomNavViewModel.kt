package com.example.tbilisi_parking_final_exm.presentation.screen.bottomNavFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.ClearDataStoreUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.bottomNav.BottomNavEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomNavViewModel @Inject constructor(
    private val clearDataStore: ClearDataStoreUseCase
): ViewModel() {

    private val _uiEvent = MutableSharedFlow<BottomNavUiEvent>()
    val uiEvent get() = _uiEvent

    fun onEvent(event: BottomNavEvent ) {
        when(event){
            is BottomNavEvent.ClearDataStore -> clearDatastore()
        }
    }

    private fun clearDatastore() {
        viewModelScope.launch {
            clearDataStore()
            _uiEvent.emit(
                BottomNavUiEvent.NavigateToHomeFragment
            )
        }

    }

    sealed interface BottomNavUiEvent {
        data object NavigateToHomeFragment:BottomNavUiEvent
    }

}