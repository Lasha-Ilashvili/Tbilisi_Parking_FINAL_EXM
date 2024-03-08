package com.example.tbilisi_parking_final_exm.domain.repository.map

import com.example.tbilisi_parking_final_exm.domain.model.map.GetMarkerLocation


interface MarkerLocationsRepository {
    suspend fun getMarkerLocations(jsonString: String): List<GetMarkerLocation>
}