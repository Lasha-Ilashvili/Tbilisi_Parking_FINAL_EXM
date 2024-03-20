package com.example.tbilisi_parking_final_exm.data.service.parking.delete_vehicle

import com.example.tbilisi_parking_final_exm.data.model.parking.vehicle.VehicleDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteVehicleService {

    @DELETE("car/{carId}")
    suspend fun deleteVehicle(@Path("carId") vehicleId: Int):Response<VehicleDto>
}