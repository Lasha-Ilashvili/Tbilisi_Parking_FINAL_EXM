package com.example.tbilisi_parking_final_exm.presentation.mapper.parking.start_parking

import com.example.tbilisi_parking_final_exm.domain.model.parking.start_parking.GetStartParking
import com.example.tbilisi_parking_final_exm.presentation.model.parking.start_parking.StartParking

fun StartParking.toDomain() = GetStartParking(
    stationExternalId = stationExternalId, carId = carId

)