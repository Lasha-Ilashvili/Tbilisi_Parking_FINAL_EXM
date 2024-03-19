package com.example.tbilisi_parking_final_exm.data.mapper.parking.finish_parking

import com.example.tbilisi_parking_final_exm.data.model.parking.finish_parking.ParkingIsFinishedDto
import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetParkingIsFinished

fun ParkingIsFinishedDto.toDomain() = GetParkingIsFinished(
    id = id,
    carId = carId,
    stationExternalId = stationExternalId,
    startDate = startDate,
    endDate = endDate,
    status = status,
    totalCost = totalCost,
    parkingTypeResponse = GetParkingIsFinished.ParkingTypeResponse(
        id = parkingTypeResponse.id,
        name = parkingTypeResponse.name,
        pricePerHour = parkingTypeResponse.pricePerHour
    )
)