package com.example.tbilisi_parking_final_exm.presentation.event.parking.vehicle_bottom_sheet

sealed class VehicleBottomSheetEvent {
    data class DeleteVehicle(val vehicleId: Int) : VehicleBottomSheetEvent()

    data object ResetErrorMessage : VehicleBottomSheetEvent()
}