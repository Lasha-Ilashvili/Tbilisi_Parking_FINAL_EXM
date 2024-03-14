package com.example.tbilisi_parking_final_exm.presentation.mapper.vehicle

import com.example.tbilisi_parking_final_exm.domain.model.vehicle.edit_vehicle.GetEditVehicle
import com.example.tbilisi_parking_final_exm.presentation.model.vehicle.edit_vehicle.EditVehicle

fun EditVehicle.toDomain() = GetEditVehicle(carId = carId, name = name)