package com.example.tbilisi_parking_final_exm.data.mapper.map

import com.example.tbilisi_parking_final_exm.data.model.map.MarkerLocationDto
import com.example.tbilisi_parking_final_exm.domain.model.map.GetMarkerLocation


fun MarkerLocationDto.toDomain() = GetMarkerLocation(
    lotNumber = lotNumber,
    address = address
)