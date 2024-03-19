package com.example.tbilisi_parking_final_exm.presentation.screen.parking.start_parking.parking_is_started

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.parking.finish_parking.FinishParkingUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking.parking_is_started.ParkingIsStartedEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.parking.finish_parking.toDomain
import com.example.tbilisi_parking_final_exm.presentation.model.parking.finish_parking.FinishParking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkingIsStartedViewModel @Inject constructor(
    private val finishParkingUseCase: FinishParkingUseCase
) : ViewModel() {

    private val _parkingIsStartedUiEvent = MutableSharedFlow<ParkingIsStartedUiEvent>()
    val parkingIsStartedUiEvent get() = _parkingIsStartedUiEvent

    fun onEvent(event: ParkingIsStartedEvent) {
        when (event) {

            is ParkingIsStartedEvent.FinishParking -> finishParking(
                stationExternalId = event.stationExternalId,
                carId = event.carId
            )
        }
    }

    private fun finishParking(stationExternalId: String, carId: Int) {
        viewModelScope.launch {
            finishParkingUseCase(
                finishParking = FinishParking(
                    stationExternalId = stationExternalId,
                    carId = carId
                ).toDomain()
            ).collect {
                when (it) {
                    is Resource.Success -> {
                        _parkingIsStartedUiEvent.emit(ParkingIsStartedUiEvent.NavigateToParkingFragment)
                    }

                    is Resource.Loading -> {}

                    is Resource.Error -> {}
                }
            }
        }
    }

    sealed interface ParkingIsStartedUiEvent{
        data object NavigateToParkingFragment:ParkingIsStartedUiEvent
    }

}