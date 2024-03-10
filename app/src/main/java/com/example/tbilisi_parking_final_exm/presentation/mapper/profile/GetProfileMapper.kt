package com.example.tbilisi_parking_final_exm.presentation.mapper.profile

import com.example.tbilisi_parking_final_exm.domain.model.profile.GetProfile
import com.example.tbilisi_parking_final_exm.presentation.model.profile.Profile

fun GetProfile.toPresenter() = Profile(
    id = id, fullName = "$firstName $lastName", email = email, phoneNumber = phoneNumber

)