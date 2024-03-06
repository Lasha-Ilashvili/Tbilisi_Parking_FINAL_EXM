package com.example.tbilisi_parking_final_exm.presentation.state.user_panel_bottom_sheet

import com.example.tbilisi_parking_final_exm.R

data class UserPanelBottomSheetState(
    val icon: Int,
    val title: String,
    val id: Int
) {
    companion object {
        val bottomSheetList = listOf(
            UserPanelBottomSheetState(R.drawable.icon_user_profile, "Profile", 0),
            UserPanelBottomSheetState(R.drawable.icon_user_wallet, "Wallet", 1),
            UserPanelBottomSheetState(R.drawable.icon_user_settings, "Settings", 2)
        )
    }
}
