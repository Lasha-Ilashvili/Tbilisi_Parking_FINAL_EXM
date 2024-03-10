package com.example.tbilisi_parking_final_exm.domain.model.map

import com.google.android.gms.maps.model.LatLng


data class GetMarkerLocation(
    val lotNumber: String,
    val address: String,
    val latLng: LatLng
)
