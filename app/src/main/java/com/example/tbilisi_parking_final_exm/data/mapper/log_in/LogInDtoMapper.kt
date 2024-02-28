package com.example.tbilisi_parking_final_exm.data.mapper.log_in

import com.example.tbilisi_parking_final_exm.data.model.log_in.LogInDto
import com.example.tbilisi_parking_final_exm.domain.model.log_in.GetToken

fun LogInDto.toDomain() =
    GetToken(
        accessToken = accessToken,
        refreshToken = refreshToken,
        idToken = idToken,
        tokenType = tokenType,
        expiresIn = expiresIn
    )