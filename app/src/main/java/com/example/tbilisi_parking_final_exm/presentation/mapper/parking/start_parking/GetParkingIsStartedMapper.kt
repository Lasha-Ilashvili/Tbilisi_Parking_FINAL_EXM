package com.example.tbilisi_parking_final_exm.presentation.mapper.parking.start_parking

import com.example.tbilisi_parking_final_exm.domain.model.parking.start_parking.GetParkingIsStarted
import com.example.tbilisi_parking_final_exm.presentation.model.parking.start_parking.ParkingIsStarted

fun GetParkingIsStarted.toPresenter() = ParkingIsStarted(
    id = id,
    carId = carId,
    stationExternalId = stationExternalId,
    startDate = startDate,
    status = status,
    parkingTypeResponse = ParkingIsStarted.ParkingTypeResponse(
        id = parkingTypeResponse.id,
        name = parkingTypeResponse.name,
        pricePerHour = parkingTypeResponse.pricePerHour
    )

)