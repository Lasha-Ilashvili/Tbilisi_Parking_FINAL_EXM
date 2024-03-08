package com.example.tbilisi_parking_final_exm.domain.repository.map

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.map.GetMarkerLocation
import kotlinx.coroutines.flow.Flow


interface MarkerLocationsRepository {
    fun getMarkerLocations(jsonString: String): Flow<Resource<List<GetMarkerLocation>>>
}