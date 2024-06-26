package com.example.tbilisi_parking_final_exm.data.mapper.sign_up

import com.example.tbilisi_parking_final_exm.data.model.sign_up.SignUpDto
import com.example.tbilisi_parking_final_exm.domain.model.sign_up.GetUserCredentials


fun SignUpDto.toDomain(password: String) = GetUserCredentials(
    email = email,
    password = password
)