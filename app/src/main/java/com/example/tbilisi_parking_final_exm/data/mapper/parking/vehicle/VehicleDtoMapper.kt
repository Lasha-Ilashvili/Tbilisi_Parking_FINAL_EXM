package com.example.tbilisi_parking_final_exm.data.mapper.parking.vehicle

import com.example.tbilisi_parking_final_exm.data.model.parking.vehicle.VehicleDto
import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.vehicle.GetVehicle

fun VehicleDto.toDomain() = GetVehicle(id = id, name = name, plateNumber = plateNumber)