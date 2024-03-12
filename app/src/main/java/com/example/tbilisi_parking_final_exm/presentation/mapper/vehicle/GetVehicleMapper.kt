package com.example.tbilisi_parking_final_exm.presentation.mapper.vehicle

import com.example.tbilisi_parking_final_exm.domain.model.vehicle.vehicle.GetVehicle
import com.example.tbilisi_parking_final_exm.presentation.model.vehicle.vehicle.Vehicle


fun GetVehicle.toPresenter() = Vehicle(id = id, name = name, plateNumber = plateNumber)