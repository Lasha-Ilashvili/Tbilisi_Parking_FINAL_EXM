package com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.cards.UserCardDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface GetUserCardsService {
    @GET("card/user/{id}")
    suspend fun getUserCards(@Path("id") id: Int): Response<List<UserCardDto>>
}