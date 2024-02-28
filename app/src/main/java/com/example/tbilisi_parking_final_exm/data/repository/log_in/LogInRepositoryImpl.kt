package com.example.tbilisi_parking_final_exm.data.repository.log_in

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.log_in.toData
import com.example.tbilisi_parking_final_exm.data.mapper.log_in.toDomain
import com.example.tbilisi_parking_final_exm.data.service.LogInService
import com.example.tbilisi_parking_final_exm.domain.model.log_in.GetToken
import com.example.tbilisi_parking_final_exm.domain.model.log_in.GetUser
import com.example.tbilisi_parking_final_exm.domain.repository.log_in.LogInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val logInService: LogInService
): LogInRepository {
    override suspend fun logIn(user: GetUser): Flow<Resource<GetToken>> {
        return handleResponse.safeApiCall {
            logInService.logIn(grantType = "custom_password", scope = "openid,profile", username = user.email, password = user.password)
        }.asResource {
            it.toDomain()
        }
    }
}