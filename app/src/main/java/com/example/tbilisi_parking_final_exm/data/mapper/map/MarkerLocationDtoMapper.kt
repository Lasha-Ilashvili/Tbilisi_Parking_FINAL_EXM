package com.example.tbilisi_parking_final_exm.data.mapper.map

import com.example.tbilisi_parking_final_exm.data.model.map.MarkerLocationDto
import com.example.tbilisi_parking_final_exm.domain.model.map.GetMarkerLocation
import com.google.android.gms.maps.model.LatLng


fun MarkerLocationDto.toDomain(latLng: LatLng) = GetMarkerLocation(
    lotNumber = lotNumber,
    address = address,
    latLng = latLng
)