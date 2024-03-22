package com.example.tbilisi_parking_final_exm.presentation.state.user_panel.profile

import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.profile.Profile

data class ProfileState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val profile: Profile? = null,
    val sessionCompleted: Boolean = false

)

