package com.example.tbilisi_parking_final_exm.presentation.state.parking.vehicle_bottom_sheet

import com.example.tbilisi_parking_final_exm.R

enum class VehicleBottomSheetState(val icon: Int, val title: String, val id: Int) {

    EDIT(R.drawable.ic_edit, "Edit", 0),
    ACTIVE_LICENSES(R.drawable.ic_parking_car, "Active Licenses", 1),
    DELETE(R.drawable.ic_remove, "Remove Vehicle", 2);

    companion object {
        val bottomSheetList = listOf(EDIT, ACTIVE_LICENSES, DELETE)
    }
}
