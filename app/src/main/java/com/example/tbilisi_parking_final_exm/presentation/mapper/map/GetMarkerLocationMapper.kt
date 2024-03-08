package com.example.tbilisi_parking_final_exm.presentation.mapper.map

import com.example.tbilisi_parking_final_exm.domain.model.map.GetMarkerLocation
import com.example.tbilisi_parking_final_exm.presentation.model.map.MarkerLocation


fun GetMarkerLocation.toPresentation() = MarkerLocation(
    lotNumber = lotNumber,
    address = address,
    latLng = latLng
)