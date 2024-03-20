package com.example.tbilisi_parking_final_exm.data.mapper.parking.finish_parking

import com.example.tbilisi_parking_final_exm.data.model.parking.finish_parking.FinishParkingDto
import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetFinishParking

fun GetFinishParking.toData() = FinishParkingDto(
    stationExternalId = stationExternalId, carId = carId

)