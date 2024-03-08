package com.example.tbilisi_parking_final_exm.domain.usecase.map

import com.example.tbilisi_parking_final_exm.domain.repository.map.MarkerLocationsRepository
import javax.inject.Inject

class MarkerLocationsUseCase @Inject constructor(
    private val markerLocationsRepository: MarkerLocationsRepository
) {
    operator fun invoke(jsonString: String) =
        markerLocationsRepository.getMarkerLocations(jsonString = jsonString)
}