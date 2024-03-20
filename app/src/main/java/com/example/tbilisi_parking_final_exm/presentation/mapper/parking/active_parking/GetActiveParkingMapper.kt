package com.example.tbilisi_parking_final_exm.presentation.mapper.parking.active_parking

import com.example.tbilisi_parking_final_exm.domain.model.parking.active_parking.GetActiveParking
import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetParkingIsFinished
import com.example.tbilisi_parking_final_exm.presentation.model.parking.active_parking.ActiveParking

fun GetActiveParking.toPresenter() = ActiveParking(
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