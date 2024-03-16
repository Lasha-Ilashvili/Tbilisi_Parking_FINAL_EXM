package com.example.tbilisi_parking_final_exm.data.service.parking.edit_vehicle

import com.example.tbilisi_parking_final_exm.data.model.parking.edit_vehicle.EditVehicleDto
import com.example.tbilisi_parking_final_exm.data.model.parking.vehicle.VehicleDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH

interface EditVehicleService {
    @PATCH("car")
    suspend fun editVehicle(@Body vehicle: EditVehicleDto): Response<VehicleDto>
}