package com.example.tbilisi_parking_final_exm.presentation.event.map

import com.google.android.gms.maps.model.LatLng


sealed class MapEvent {
    data class SetUserLocation(val userLatLng: LatLng) : MapEvent()
    data class SetMarkers(val jsonString: String) : MapEvent()
}