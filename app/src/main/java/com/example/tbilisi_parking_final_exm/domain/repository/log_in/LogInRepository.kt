package com.example.tbilisi_parking_final_exm.domain.repository.log_in

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.log_in.GetToken
import com.example.tbilisi_parking_final_exm.domain.model.log_in.GetUser
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    suspend fun logIn(user: GetUser): Flow<Resource<GetToken>>

}