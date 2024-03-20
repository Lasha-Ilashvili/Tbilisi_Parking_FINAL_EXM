package com.example.tbilisi_parking_final_exm.presentation.event.parking

sealed class ParkingEvent {
    data object CheckActiveParking : ParkingEvent()
    data object FetchAllVehicle :ParkingEvent()
    data object ResetErrorMessage :ParkingEvent()
    data object GetUserBalance:ParkingEvent()
}