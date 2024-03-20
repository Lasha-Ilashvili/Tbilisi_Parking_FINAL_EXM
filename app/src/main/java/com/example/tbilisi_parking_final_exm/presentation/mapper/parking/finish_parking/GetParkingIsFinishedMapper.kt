package com.example.tbilisi_parking_final_exm.presentation.mapper.parking.finish_parking

import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetParkingIsFinished
import com.example.tbilisi_parking_final_exm.presentation.model.parking.finish_parking.ParkingIsFinished

fun GetParkingIsFinished.toPresenter() = ParkingIsFinished(
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