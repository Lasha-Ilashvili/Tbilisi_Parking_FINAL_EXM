package com.example.tbilisi_parking_final_exm.data.service.add_vehicle

import com.example.tbilisi_parking_final_exm.data.model.vehicle.vehicle.VehicleDto
import com.example.tbilisi_parking_final_exm.domain.model.vehicle.add_vehicle.GetAddVehicle
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddVehicleService {

    @POST("car")
    suspend fun addVehicle(@Body addVehicle: GetAddVehicle): Response<VehicleDto>

}