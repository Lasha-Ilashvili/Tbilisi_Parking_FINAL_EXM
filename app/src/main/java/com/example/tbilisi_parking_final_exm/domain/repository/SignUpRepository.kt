package com.example.tbilisi_parking_final_exm.domain.repository

import com.example.tbilisi_parking_final_exm.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {

    fun signUp(
        email: String,
        password: String,
        personalNumber: Int,
        firstName: String,
        lastName: String,
        phoneNumber: Int
    ): Flow<Resource<Pair<String, String>>>
}