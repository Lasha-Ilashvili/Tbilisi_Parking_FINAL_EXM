package com.example.tbilisi_parking_final_exm.data.service.parking.start_parking

import com.example.tbilisi_parking_final_exm.data.model.parking.start_parking.ParkingIsStartedDto
import com.example.tbilisi_parking_final_exm.data.model.parking.start_parking.StartParkingDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StartParkingService {
    @POST("parking/start")
    suspend fun startParking(@Body startParking: StartParkingDto): Response<ParkingIsStartedDto>
}