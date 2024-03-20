package com.example.tbilisi_parking_final_exm.data.mapper.parking.start_parking

import com.example.tbilisi_parking_final_exm.data.model.parking.start_parking.ParkingIsStartedDto
import com.example.tbilisi_parking_final_exm.domain.model.parking.start_parking.GetParkingIsStarted

fun ParkingIsStartedDto.toDomain() = GetParkingIsStarted(
    id = id,
    carId = carId,
    stationExternalId = stationExternalId,
    startDate = startDate,
    status = status,
    parkingTypeResponse = GetParkingIsStarted.ParkingTypeResponse(
        id = parkingTypeResponse.id,
        name = parkingTypeResponse.name,
        pricePerHour = parkingTypeResponse.pricePerHour
    )

)