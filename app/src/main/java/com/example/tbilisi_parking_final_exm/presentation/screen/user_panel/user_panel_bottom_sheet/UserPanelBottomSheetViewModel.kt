package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.user_panel_bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.ClearDataStoreUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.user_panel_bottom_sheet.UserPanelBottomSheetEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPanelBottomSheetViewModel @Inject constructor(
    private val clearDataStore: ClearDataStoreUseCase
): ViewModel() {

    fun onEvent(event: UserPanelBottomSheetEvent) {
        when (event) {
            is UserPanelBottomSheetEvent.ClearDataStore -> clearData()
        }
    }

    private fun clearData() {
        viewModelScope.launch {
            clearDataStore.invoke()
        }
    }
}