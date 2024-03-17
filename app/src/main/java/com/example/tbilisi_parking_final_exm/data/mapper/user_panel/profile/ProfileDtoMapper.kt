package com.example.tbilisi_parking_final_exm.data.mapper.user_panel.profile

import com.example.tbilisi_parking_final_exm.data.model.user_panel.profile.ProfileDto
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.profile.GetProfile

fun ProfileDto.toDomain() = GetProfile(
    id = id, firstName = firstname, lastName = lastname, email = email, phoneNumber = phoneNumber
)