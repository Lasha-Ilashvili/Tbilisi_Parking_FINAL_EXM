package com.example.tbilisi_parking_final_exm.presentation.mapper.parking.vehicle

import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.vehicle.GetVehicle
import com.example.tbilisi_parking_final_exm.presentation.model.parking.vehicle.vehicle.Vehicle


fun GetVehicle.toPresenter() = Vehicle(id = id, name = name, plateNumber = plateNumber)