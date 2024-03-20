package com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking.parking_is_started

sealed class ParkingIsStartedEvent {

    data class StartTimer(val parkingStartedAt: String) :ParkingIsStartedEvent()
    data class FinishParking(val stationExternalId: String, val carId: Int) : ParkingIsStartedEvent()

}