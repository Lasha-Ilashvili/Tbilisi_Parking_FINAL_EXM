package com.example.tbilisi_parking_final_exm.presentation.state.user_panel_bottom_sheet

import com.example.tbilisi_parking_final_exm.R

enum class UserPanelBottomSheetState(val icon: Int, val title: String, val id: Int) {
    PROFILE(R.drawable.icon_user_profile, "Profile", 0),
    WALLET(R.drawable.icon_user_wallet, "Wallet", 1),
    SETTINGS(R.drawable.icon_user_settings, "Settings", 2);

    companion object {
        val bottomSheetList = listOf(PROFILE, WALLET, SETTINGS)
    }
}
