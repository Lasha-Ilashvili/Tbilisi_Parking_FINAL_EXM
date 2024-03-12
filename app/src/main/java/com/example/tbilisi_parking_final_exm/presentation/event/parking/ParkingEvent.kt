package com.example.tbilisi_parking_final_exm.presentation.event.parking

sealed class ParkingEvent {
    data object FetchAllVehicle :ParkingEvent()
}