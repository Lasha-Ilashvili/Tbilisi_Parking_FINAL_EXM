package com.example.tbilisi_parking_final_exm.domain.repository.sign_up

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.sign_up.GetUser
import com.example.tbilisi_parking_final_exm.domain.model.sign_up.GetUserCredentials
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    suspend fun signUp(
        getUser: GetUser
    ): Flow<Resource<GetUserCredentials>>
}