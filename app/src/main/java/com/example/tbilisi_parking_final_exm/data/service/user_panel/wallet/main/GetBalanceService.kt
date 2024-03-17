package com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.main

import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.main.BalanceDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface GetBalanceService {
    @GET("balance/user/{userId}")
    suspend fun getBalance(@Path("userId") userId: Int): Response<BalanceDto>
}