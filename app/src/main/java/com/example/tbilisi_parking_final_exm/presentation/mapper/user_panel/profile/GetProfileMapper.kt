package com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.profile

import com.example.tbilisi_parking_final_exm.domain.model.user_panel.profile.GetProfile
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.profile.Profile

fun GetProfile.toPresenter() = Profile(
    id = id, fullName = "$firstName $lastName", email = email, phoneNumber = phoneNumber

)