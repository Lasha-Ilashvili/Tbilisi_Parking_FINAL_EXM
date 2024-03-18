package com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteUserCardService {
    @DELETE("card/{cardId}")
    suspend fun deleteUserCard(@Path("cardId") cardId: Int): Response<Unit>
}