package com.example.tbilisi_parking_final_exm.data.mapper.parking.vehicle

import com.example.tbilisi_parking_final_exm.data.model.parking.edit_vehicle.EditVehicleDto
import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.edit_vehicle.GetEditVehicle

fun GetEditVehicle.toData() = EditVehicleDto(carId = carId, name = name)