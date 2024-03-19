package com.example.tbilisi_parking_final_exm.presentation.mapper.parking.vehicle

import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.add_vehicle.GetAddVehicle
import com.example.tbilisi_parking_final_exm.presentation.model.parking.vehicle.add_vehicle.AddVehicle

fun AddVehicle.toDomain() = GetAddVehicle(userId = userId, name = name, plateNumber = plateNumber)