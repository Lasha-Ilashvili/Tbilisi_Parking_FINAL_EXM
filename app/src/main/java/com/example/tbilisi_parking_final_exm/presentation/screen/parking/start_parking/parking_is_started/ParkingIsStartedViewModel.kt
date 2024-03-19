package com.example.tbilisi_parking_final_exm.presentation.screen.parking.start_parking.parking_is_started

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.domain.usecase.parking.start_parking.StartParkingUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking.parking_is_started.ParkingIsStartedEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.parking.start_parking.toDomain
import com.example.tbilisi_parking_final_exm.presentation.model.parking.start_parking.StartParking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkingIsStartedViewModel @Inject constructor(
    private val startParkingUseCase: StartParkingUseCase
) : ViewModel() {

    fun onEvent(event: ParkingIsStartedEvent) {
        when (event) {
            is ParkingIsStartedEvent.StartParking -> startParking(
                stationExternalId = event.stationExternalId,
                carId = event.carId
            )
        }
    }

    private fun startParking(stationExternalId: String, carId: Int) {
        viewModelScope.launch {
            startParkingUseCase(
                startParking = StartParking(
                    stationExternalId = stationExternalId,
                    carId = carId
                ).toDomain()
            ).collect {
                println(it)
            }
        }
    }

}