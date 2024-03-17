package com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.cards.CardDetailsDto
import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.cards.CardIdDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SaveCardService {
    @POST("card")
    suspend fun saveCard(@Body cardDetails: CardDetailsDto): Response<CardIdDto>
}