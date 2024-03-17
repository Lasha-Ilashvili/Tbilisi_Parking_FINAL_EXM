package com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteRememberedCardService {
    @DELETE("card/{cardId}")
    suspend fun deleteRememberedCard(@Path("cardId") cardId: Int): Response<ResponseBody>
}