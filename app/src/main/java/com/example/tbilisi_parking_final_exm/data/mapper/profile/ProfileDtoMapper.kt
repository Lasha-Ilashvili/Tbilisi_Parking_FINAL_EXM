package com.example.tbilisi_parking_final_exm.data.mapper.profile

import com.example.tbilisi_parking_final_exm.data.model.profile.ProfileDto
import com.example.tbilisi_parking_final_exm.domain.model.profile.GetProfile

fun ProfileDto.toDomain() = GetProfile(
    id = id, firstName = firstname, lastName = lastname, email = email, phoneNumber = phoneNumber
)