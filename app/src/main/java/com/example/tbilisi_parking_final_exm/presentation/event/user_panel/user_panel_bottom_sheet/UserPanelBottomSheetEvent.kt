package com.example.tbilisi_parking_final_exm.presentation.event.user_panel.user_panel_bottom_sheet

sealed class UserPanelBottomSheetEvent {
    data object ClearDataStore :UserPanelBottomSheetEvent()
}