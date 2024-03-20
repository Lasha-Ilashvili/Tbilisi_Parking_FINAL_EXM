package com.example.tbilisi_parking_final_exm.data.service.parking.active_parking

import com.example.tbilisi_parking_final_exm.data.model.parking.active_parking.ActiveParkingDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetActiveParkingService {

    @GET("parking/user/{userId}")
    suspend fun getActiveParking(
        @Path("userId") userId :Int
    ): Response<List<ActiveParkingDto>>
}