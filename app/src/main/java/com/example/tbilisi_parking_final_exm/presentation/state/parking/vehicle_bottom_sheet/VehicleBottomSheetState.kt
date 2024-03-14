package com.example.tbilisi_parking_final_exm.presentation.state.parking.vehicle_bottom_sheet

import com.example.tbilisi_parking_final_exm.R

enum class VehicleBottomSheetState(val icon: Int, val title: String, val id: Int){

    EDIT(R.drawable.ic_edit, "Edit", 0),
    DELETE(R.drawable.ic_remove, "Remove Vehicle", 1);

    companion object {
        val bottomSheetList = listOf(EDIT, DELETE)
    }
}
