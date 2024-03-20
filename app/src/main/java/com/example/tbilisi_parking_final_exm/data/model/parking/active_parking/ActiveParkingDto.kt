package com.example.tbilisi_parking_final_exm.data.model.parking.active_parking

import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetParkingIsFinished

data class ActiveParkingDto(
    val id: Int,
    val carId: Int,
    val stationExternalId: String,
    val startDate: String,
    val status: String,
    val parkingTypeResponse: GetParkingIsFinished.ParkingTypeResponse

) {
    data class ParkingTypeResponse(
        val id: Int,
        val name: String,
        val pricePerHour: Int
    )
}