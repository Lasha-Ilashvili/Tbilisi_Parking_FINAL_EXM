package com.example.tbilisi_parking_final_exm.data.mapper.sign_up

import com.example.tbilisi_parking_final_exm.data.model.sign_up.UserDto
import com.example.tbilisi_parking_final_exm.domain.model.sign_up.GetUser

fun GetUser.toData() = UserDto(
    firstName = firstName,
    lastName = lastName,
    email = email,
    mobileNumber = mobileNumber,
    password = password,
    matchingPassword = matchingPassword,
    personalNumber = personalNumber
)