package com.example.tbilisi_parking_final_exm.presentation.mapper.sign_up

import com.example.tbilisi_parking_final_exm.domain.model.sign_up.GetUserCredentials
import com.example.tbilisi_parking_final_exm.presentation.model.sign_up.UserCredentials


fun GetUserCredentials.toPresentation() = UserCredentials(
    email = email,
    password = password
)