package com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking.parking_is_started

sealed class ParkingIsStartedEvent {
    data class StartParking(val stationExternalId: String, val carId: Int) : ParkingIsStartedEvent()

    data class FinishParking(val stationExternalId: String, val carId: Int) : ParkingIsStartedEvent()

}