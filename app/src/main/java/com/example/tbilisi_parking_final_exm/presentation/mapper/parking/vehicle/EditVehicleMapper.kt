package com.example.tbilisi_parking_final_exm.presentation.mapper.parking.vehicle

import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.edit_vehicle.GetEditVehicle
import com.example.tbilisi_parking_final_exm.presentation.model.parking.vehicle.edit_vehicle.EditVehicle

fun EditVehicle.toDomain() = GetEditVehicle(carId = carId, name = name)