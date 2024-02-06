package com.example.tbilisi_parking_final_exm.data.repository

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.model.User
import com.example.tbilisi_parking_final_exm.data.service.SignUpService
import com.example.tbilisi_parking_final_exm.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val signUpService: SignUpService,
    private val handleResponse: HandleResponse
) : SignUpRepository {

    override suspend fun signUp(
        email: String,
        password: String,
        personalNumber: Int,
        firstName: String,
        lastName: String,
        phoneNumber: Int
    ): Flow<Resource<Pair<String, String>>> {
        return handleResponse.safeApiCall {
            signUpService.signUp(User(email, password))
        }.asResource {
            Pair(email, password)
        }
    }
}