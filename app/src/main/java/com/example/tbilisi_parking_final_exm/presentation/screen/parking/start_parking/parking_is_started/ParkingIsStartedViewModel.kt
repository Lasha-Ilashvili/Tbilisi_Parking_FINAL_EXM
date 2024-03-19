package com.example.tbilisi_parking_final_exm.presentation.screen.parking.start_parking.parking_is_started

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.parking.finish_parking.FinishParkingUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.parking.start_parking.StartParkingUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking.parking_is_started.ParkingIsStartedEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.parking.finish_parking.toDomain
import com.example.tbilisi_parking_final_exm.presentation.mapper.parking.start_parking.toDomain
import com.example.tbilisi_parking_final_exm.presentation.model.parking.finish_parking.FinishParking
import com.example.tbilisi_parking_final_exm.presentation.model.parking.start_parking.StartParking
import com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking.ParkingIsStartedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkingIsStartedViewModel @Inject constructor(
    private val startParkingUseCase: StartParkingUseCase,
    private val finishParkingUseCase: FinishParkingUseCase
) : ViewModel() {

    private val _parkingIsStartedState = MutableStateFlow(ParkingIsStartedState())
    val parkingIsStartedState: SharedFlow<ParkingIsStartedState>  = _parkingIsStartedState.asStateFlow()

    private val _parkingIsStartedUiEvent = MutableSharedFlow<ParkingIsStartedUiEvent>()
    val parkingIsStartedUiEvent get() = _parkingIsStartedUiEvent

    fun onEvent(event: ParkingIsStartedEvent) {
        when (event) {
            is ParkingIsStartedEvent.StartParking -> startParking(
                stationExternalId = event.stationExternalId,
                carId = event.carId
            )

            is ParkingIsStartedEvent.FinishParking -> finishParking(
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
                println("parking is started: $it")
            }
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

                    is Resource.Loading -> _parkingIsStartedState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
                println("parking is finished : $it")
            }
        }
    }

    private fun updateErrorMessage(message: String? = null) {
        _parkingIsStartedState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    sealed interface ParkingIsStartedUiEvent{
        data object NavigateToParkingFragment:ParkingIsStartedUiEvent
    }

}