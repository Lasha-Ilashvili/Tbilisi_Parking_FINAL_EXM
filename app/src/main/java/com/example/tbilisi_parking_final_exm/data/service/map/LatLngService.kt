package com.example.tbilisi_parking_final_exm.data.service.map

import com.example.tbilisi_parking_final_exm.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LatLngService {
    @GET("json")
    suspend fun getLatLng(
        @Query("address") locationName: String,
        @Query("key") apiKey: String = BuildConfig.GOOGLE_MAPS_API_KEY
    ): Response<ResponseBody>
}