package com.example.tbilisi_parking_final_exm.data.repository.sign_up

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.sign_up.toData
import com.example.tbilisi_parking_final_exm.data.mapper.sign_up.toDomain
import com.example.tbilisi_parking_final_exm.data.service.sign_up.SignUpService
import com.example.tbilisi_parking_final_exm.domain.model.sign_up.GetUserCredentials
import com.example.tbilisi_parking_final_exm.domain.model.sign_up.GetUser
import com.example.tbilisi_parking_final_exm.domain.repository.sign_up.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val signUpService: SignUpService,
    private val handleResponse: HandleResponse
) : SignUpRepository {
    override suspend fun signUp(
        getUser: GetUser
    ): Flow<Resource<GetUserCredentials>> {
        return handleResponse.safeApiCall {
            signUpService.signUp(getUser.toData())
        }.asResource {
            it.toDomain(getUser.password)
        }
    }
}