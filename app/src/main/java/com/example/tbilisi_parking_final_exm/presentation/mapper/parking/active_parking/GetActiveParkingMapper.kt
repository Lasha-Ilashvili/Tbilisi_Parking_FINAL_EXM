package com.example.tbilisi_parking_final_exm.presentation.mapper.parking.active_parking

import com.example.tbilisi_parking_final_exm.domain.model.parking.active_parking.GetActiveParking
import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetParkingIsFinished
import com.example.tbilisi_parking_final_exm.presentation.model.parking.active_parking.ActiveParking
import com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking.Zone

fun GetActiveParking.toPresenter() = ActiveParking(
    id = id,
    carId = carId,
    stationExternalId = stationExternalId,
    startDate = startDate,
    status = status,
    zone = getZone(stationExternalId),
    parkingTypeResponse = GetParkingIsFinished.ParkingTypeResponse(
        id = parkingTypeResponse.id,
        name = parkingTypeResponse.name,
        pricePerHour = parkingTypeResponse.pricePerHour
    )

)

private fun getZone(stationExternalId: String): Zone {

    return when (stationExternalId) {
        "A1" -> Zone.A
        "B1" -> Zone.B
        else -> Zone.C
    }
}