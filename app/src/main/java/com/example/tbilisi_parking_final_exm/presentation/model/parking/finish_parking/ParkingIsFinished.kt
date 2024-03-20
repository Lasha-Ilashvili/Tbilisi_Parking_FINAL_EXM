package com.example.tbilisi_parking_final_exm.presentation.model.parking.finish_parking

import com.example.tbilisi_parking_final_exm.domain.model.parking.finish_parking.GetParkingIsFinished

data class ParkingIsFinished (
    val id: Int,
    val carId: Int,
    val stationExternalId: String,
    val startDate: String,
    val endDate:String,
    val status: String,
    val totalCost: Double,
    val parkingTypeResponse: GetParkingIsFinished.ParkingTypeResponse
){
    data class ParkingTypeResponse(
        val id: Int,
        val name: String,
        val pricePerHour: Int
    )
}