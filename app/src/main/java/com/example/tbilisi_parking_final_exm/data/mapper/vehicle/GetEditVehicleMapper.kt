package com.example.tbilisi_parking_final_exm.data.mapper.vehicle

import com.example.tbilisi_parking_final_exm.data.model.vehicle.edit_vehicle.EditVehicleDto
import com.example.tbilisi_parking_final_exm.domain.model.vehicle.edit_vehicle.GetEditVehicle

fun GetEditVehicle.toData() = EditVehicleDto(carId = carId, name = name)