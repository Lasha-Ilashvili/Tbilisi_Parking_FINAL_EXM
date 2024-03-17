package com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.main

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.main.toDomain
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.main.GetBalanceService
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.main.GetBalance
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.main.GetBalanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBalanceRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val getBalanceService: GetBalanceService
) : GetBalanceRepository {

    override suspend fun getBalance(userId: Int): Flow<Resource<GetBalance>> {
        return handleResponse.safeApiCall {
            getBalanceService.getBalance(userId)
        }.asResource {
            it.toDomain()
        }
    }
}