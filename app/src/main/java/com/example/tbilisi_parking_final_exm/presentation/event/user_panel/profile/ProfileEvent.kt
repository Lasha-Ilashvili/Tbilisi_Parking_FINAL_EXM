package com.example.tbilisi_parking_final_exm.presentation.event.user_panel.profile

sealed class ProfileEvent {
    data object FetchUserProfile : ProfileEvent()
    data object ResetErrorMessage: ProfileEvent()
}