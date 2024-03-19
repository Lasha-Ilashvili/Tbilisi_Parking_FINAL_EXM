package com.example.tbilisi_parking_final_exm.presentation.model.parking.start_parking

data class ParkingIsStarted(
    val id: Int,
    val carId: Int,
    val stationExternalId: String,
    val startDate: String,
    val status: String,
    val parkingTypeResponse: ParkingTypeResponse,
) {
    data class ParkingTypeResponse(
        val id: Int,
        val name: String,
        val pricePerHour: Int
    )
}