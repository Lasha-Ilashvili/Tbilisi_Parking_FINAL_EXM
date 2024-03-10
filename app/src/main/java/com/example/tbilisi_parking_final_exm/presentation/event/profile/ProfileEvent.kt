package com.example.tbilisi_parking_final_exm.presentation.event.profile

sealed class ProfileEvent {
    data object FetchUserProfile :ProfileEvent()
    data object ResetErrorMessage: ProfileEvent()
}