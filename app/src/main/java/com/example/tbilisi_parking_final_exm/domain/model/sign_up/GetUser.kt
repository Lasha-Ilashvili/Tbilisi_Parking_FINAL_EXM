package com.example.tbilisi_parking_final_exm.domain.model.sign_up


data class GetUser(
    val firstName: String,
    val lastName: String,
    val email: String,
    val mobileNumber: String,
    val password: String,
    val matchingPassword: String,
    val personalNumber: String
)