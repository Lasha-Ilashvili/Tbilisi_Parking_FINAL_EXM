package com.example.tbilisi_parking_final_exm.presentation.model.map

import com.google.android.gms.maps.model.LatLng


data class MarkerLocation(
    val lotNumber: String,
    val address: String,
    val latLng: LatLng
)
