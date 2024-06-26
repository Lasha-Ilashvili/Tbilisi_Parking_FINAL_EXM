package com.example.tbilisi_parking_final_exm.data.service.parking.get_vehicle

import com.example.tbilisi_parking_final_exm.data.model.parking.vehicle.VehicleDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetAllVehicleService {

    @GET("car/user")
    suspend fun getAllVehicle(
        @Query("userId") userId: Int
    ): Response<List<VehicleDto>>
}