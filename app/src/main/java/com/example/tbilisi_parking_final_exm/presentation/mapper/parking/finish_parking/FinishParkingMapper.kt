package com.example.tbilisi_parking_final_exm.presentation.mapper.parking.finish_parking

import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetFinishParking
import com.example.tbilisi_parking_final_exm.presentation.model.parking.finish_parking.FinishParking

fun FinishParking.toDomain() = GetFinishParking(
    stationExternalId = stationExternalId, carId = carId

)