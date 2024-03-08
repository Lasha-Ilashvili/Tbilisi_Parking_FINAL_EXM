package com.example.tbilisi_parking_final_exm.domain.usecase.map

import com.example.tbilisi_parking_final_exm.domain.repository.map.LatLngRepository
import javax.inject.Inject

class LatLngUseCase @Inject constructor(
    private val latLngRepository: LatLngRepository
) {
    suspend operator fun invoke(address: String) =
        latLngRepository.locationNameToLatLng(address = address)
}