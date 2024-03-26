package com.example.tbilisi_parking_final_exm.presentation.state.map

import com.example.tbilisi_parking_final_exm.presentation.model.map.MarkerLocation
import com.google.android.gms.maps.model.LatLng


data class MapState(
    val isLoading: Boolean = false,
    val shouldShowUserLocation: Boolean = true,
    val markerLocation: List<MarkerLocation>? = null,
    val userLatLng: LatLng? = null,
    val errorMessage: String? = null
)