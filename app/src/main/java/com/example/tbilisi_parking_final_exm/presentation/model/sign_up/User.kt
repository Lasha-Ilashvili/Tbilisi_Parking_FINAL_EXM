package com.example.tbilisi_parking_final_exm.presentation.model.sign_up


data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val mobileNumber: String,
    val password: String,
    val matchingPassword: String,
    val personalNumber: String
)
