package com.example.tbilisi_parking_final_exm.data.mapper.parking.start_parking

import com.example.tbilisi_parking_final_exm.data.model.parking.start_parking.StartParkingDto
import com.example.tbilisi_parking_final_exm.domain.model.parking.start_parking.GetStartParking

fun GetStartParking.toData() = StartParkingDto(
    stationExternalId = stationExternalId, carId = carId

)