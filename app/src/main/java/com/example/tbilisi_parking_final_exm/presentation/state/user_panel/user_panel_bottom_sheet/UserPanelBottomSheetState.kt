package com.example.tbilisi_parking_final_exm.presentation.state.user_panel.user_panel_bottom_sheet

import com.example.tbilisi_parking_final_exm.R

enum class UserPanelBottomSheetState(val icon: Int, val title: String, val id: Int) {
    PROFILE(R.drawable.ic_user_profile, "Profile", 0),
    WALLET(R.drawable.ic_user_wallet, "Wallet", 1),
    SETTINGS(R.drawable.ic_user_settings, "Settings", 2),
    SIGN_OUT(R.drawable.ic_sign_out, "Sign Out", 3);

    companion object {
        val bottomSheetList = listOf(PROFILE, WALLET, SETTINGS, SIGN_OUT)
    }
}
