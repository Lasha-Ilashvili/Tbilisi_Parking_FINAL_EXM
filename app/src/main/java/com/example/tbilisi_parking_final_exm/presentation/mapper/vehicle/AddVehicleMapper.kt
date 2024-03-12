package com.example.tbilisi_parking_final_exm.presentation.mapper.vehicle

import com.example.tbilisi_parking_final_exm.domain.model.vehicle.add_vehicle.GetAddVehicle
import com.example.tbilisi_parking_final_exm.presentation.model.vehicle.add_vehicle.AddVehicle

fun AddVehicle.toDomain() = GetAddVehicle(userId = userId, name = name, plateNumber = plateNumber)