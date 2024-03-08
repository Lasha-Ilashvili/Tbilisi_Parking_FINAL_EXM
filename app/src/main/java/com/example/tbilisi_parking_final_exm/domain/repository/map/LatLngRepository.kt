package com.example.tbilisi_parking_final_exm.domain.repository.map

import com.google.android.gms.maps.model.LatLng


interface LatLngRepository {
    suspend fun locationNameToLatLng(
        address: String
    ): LatLng?
}