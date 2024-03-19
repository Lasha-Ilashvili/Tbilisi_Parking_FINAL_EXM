package com.example.tbilisi_parking_final_exm.data.service.parking.finish_parking

import com.example.tbilisi_parking_final_exm.data.model.parking.finish_parking.FinishParkingDto
import com.example.tbilisi_parking_final_exm.data.model.parking.finish_parking.ParkingIsFinishedDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FinishParkingService {

    @POST("parking/end")
    suspend fun finishParking(@Body finishParking: FinishParkingDto) : Response<ParkingIsFinishedDto>
}