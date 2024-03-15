package com.example.tbilisi_parking_final_exm.data.mapper.new_token

import com.example.tbilisi_parking_final_exm.data.model.new_token.NewTokenDto
import com.example.tbilisi_parking_final_exm.domain.model.new_token.GetNewToken

fun NewTokenDto.toDomain() = GetNewToken(accessToken = accessToken)