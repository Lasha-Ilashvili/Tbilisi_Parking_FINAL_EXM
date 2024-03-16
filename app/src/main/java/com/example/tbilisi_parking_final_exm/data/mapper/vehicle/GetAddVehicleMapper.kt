package com.example.tbilisi_parking_final_exm.data.mapper.vehicle

import com.example.tbilisi_parking_final_exm.data.model.parking.add_vehicle.AddVehicleDto
import com.example.tbilisi_parking_final_exm.domain.model.vehicle.add_vehicle.GetAddVehicle

fun GetAddVehicle.toData() = AddVehicleDto(
    userId = userId, name = name, plateNumber = plateNumber

)