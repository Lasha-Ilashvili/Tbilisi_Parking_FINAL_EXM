package com.example.tbilisi_parking_final_exm.presentation.mapper.sign_up

import com.example.tbilisi_parking_final_exm.domain.model.sign_up.GetUser
import com.example.tbilisi_parking_final_exm.presentation.model.sign_up.User

fun User.toDomain() = GetUser(
    firstName = firstName,
    lastName = lastName,
    email = email,
    mobileNumber = mobileNumber,
    password = password,
    matchingPassword = matchingPassword,
    personalNumber = personalNumber
)