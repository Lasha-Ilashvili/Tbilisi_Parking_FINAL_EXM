package com.example.tbilisi_parking_final_exm.data.model.sign_up

import com.squareup.moshi.Json

data class UserDto(
    @Json(name = "firstname")
    val firstName:String,
    @Json(name = "lastname")
    val lastName:String,
    val email:String,
    @Json(name = "phoneNumber")
    val mobileNumber:String,
    val password:String,
    val matchingPassword:String,
    val personalNumber:String
)