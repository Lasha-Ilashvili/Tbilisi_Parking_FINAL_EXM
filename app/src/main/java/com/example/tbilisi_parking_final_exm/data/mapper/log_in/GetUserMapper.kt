package com.example.tbilisi_parking_final_exm.data.mapper.log_in

import com.example.tbilisi_parking_final_exm.data.model.log_in.UserDto
import com.example.tbilisi_parking_final_exm.domain.model.log_in.GetUser

fun GetUser.toData() = UserDto(
    email = email, password = password
)