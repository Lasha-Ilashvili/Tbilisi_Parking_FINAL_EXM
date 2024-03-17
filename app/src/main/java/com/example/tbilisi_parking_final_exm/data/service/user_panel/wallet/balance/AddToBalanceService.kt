package com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.balance.AddBalanceRequestDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AddToBalanceService {
    @POST("balance")
    suspend fun addToBalance(@Body addBalanceRequest: AddBalanceRequestDto): Response<ResponseBody>
}