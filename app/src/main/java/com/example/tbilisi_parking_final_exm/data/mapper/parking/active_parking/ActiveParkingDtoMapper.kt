package com.example.tbilisi_parking_final_exm.data.mapper.parking.active_parking

import com.example.tbilisi_parking_final_exm.data.model.parking.active_parking.ActiveParkingDto
import com.example.tbilisi_parking_final_exm.domain.model.parking.active_parking.GetActiveParking
import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetParkingIsFinished

fun ActiveParkingDto.toDomain() = GetActiveParking(
    id = id,
    carId = carId,
    stationExternalId = stationExternalId,
    startDate = startDate,
    status = status,
    parkingTypeResponse = GetParkingIsFinished.ParkingTypeResponse(
        id = parkingTypeResponse.id,
        name = parkingTypeResponse.name,
        pricePerHour = parkingTypeResponse.pricePerHour
    )

)