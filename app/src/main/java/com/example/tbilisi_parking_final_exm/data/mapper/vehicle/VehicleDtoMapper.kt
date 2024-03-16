package com.example.tbilisi_parking_final_exm.data.mapper.vehicle

import com.example.tbilisi_parking_final_exm.data.model.parking.vehicle.VehicleDto
import com.example.tbilisi_parking_final_exm.domain.model.vehicle.vehicle.GetVehicle

fun VehicleDto.toDomain() = GetVehicle(id = id, name = name, plateNumber = plateNumber)