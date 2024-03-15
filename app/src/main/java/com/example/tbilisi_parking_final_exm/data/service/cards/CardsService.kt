package com.example.tbilisi_parking_final_exm.data.service.cards

import com.example.tbilisi_parking_final_exm.data.model.cards.CardDto
import retrofit2.Response
import retrofit2.http.GET

interface CardsService {
    @GET("")
    suspend fun getCards(): Response<List<CardDto>>
}