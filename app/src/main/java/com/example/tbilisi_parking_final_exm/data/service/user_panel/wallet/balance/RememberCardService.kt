package com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.balance.CardDetailsDto
import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.balance.CardIdDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RememberCardService {
    @POST("card")
    suspend fun rememberCard(@Body cardDetails: CardDetailsDto): Response<CardIdDto>
}