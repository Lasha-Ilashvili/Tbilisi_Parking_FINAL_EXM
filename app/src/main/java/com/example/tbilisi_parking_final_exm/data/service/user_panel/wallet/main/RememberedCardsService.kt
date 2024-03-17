package com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.main

import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.main.RememberedCardDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface RememberedCardsService {
    @GET("card/user/{id}")
    suspend fun getRememberedCards(@Path("id") id: Int): Response<List<RememberedCardDto>>
}