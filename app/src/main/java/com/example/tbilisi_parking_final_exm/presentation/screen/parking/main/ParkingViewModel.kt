package com.example.tbilisi_parking_final_exm.presentation.screen.parking.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.parking.active_parking.GetActiveParking
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetUserIdUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.parking.active_parking.GetActiveParkingUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.parking.vehicle.get_vehicles.GetAllVehicleUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.balance.GetBalanceUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.ParkingEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.parking.vehicle.toPresenter
import com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.cards.toPresentation
import com.example.tbilisi_parking_final_exm.presentation.state.parking.ParkingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkingViewModel @Inject constructor(
    private val getAllVehicle: GetAllVehicleUseCase,
    private val getUserId: GetUserIdUseCase,
    private val getBalance: GetBalanceUseCase,
    private val getActiveParking: GetActiveParkingUseCase
) : ViewModel() {

    private val _parkingState = MutableStateFlow(ParkingState())
    val parkingState: SharedFlow<ParkingState> = _parkingState.asStateFlow()

    private val _parkingUiEvent = MutableSharedFlow<ParkingUiEvent>()
    val parkingUiEvent get() = _parkingUiEvent

    fun onEvent(event: ParkingEvent) {
        when (event) {
            is ParkingEvent.FetchAllVehicle -> fetchAllVehicle()
            is ParkingEvent.ResetErrorMessage -> updateErrorMessage(message = null)
            is ParkingEvent.GetUserBalance -> getUserBalance()
            is ParkingEvent.CheckActiveParking -> checkActiveParking()
        }
    }

    private fun checkActiveParking() {
        viewModelScope.launch {
            getActiveParking(userId = getUserId()).collect {
                when (it) {
                    is Resource.Error -> updateErrorMessage(it.errorMessage)
                    is Resource.Loading -> _parkingState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Success -> {
                        if (it.data.isNotEmpty()) handleData(it.data)
                    }
                    is Resource.SessionCompleted -> _parkingState.update { currentState ->
                        currentState.copy(
                            sessionCompleted = it.sessionIsCompleted
                        )
                    }
                }
            }
        }
    }

    private suspend fun handleData(data: List<GetActiveParking>) {
        if (data[0].status == "ACTIVE") {
            val activeParking = data[0]
            _parkingUiEvent.emit(
                ParkingUiEvent.NavigateToTimer(
                    stationExternalId = activeParking.stationExternalId,
                    carId = activeParking.carId,
                    startDate = activeParking.startDate,
                    zone = activeParking.stationExternalId
                )
            )
        }

    }

    private fun fetchAllVehicle() {
        viewModelScope.launch {
            getAllVehicle(userId = getUserId()).collect {
                when (it) {
                    is Resource.Loading -> _parkingState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)

                    is Resource.Success -> _parkingState.update { currentState ->
                        currentState.copy(
                            vehicles = it.data.map {
                                it.toPresenter()
                            }
                        )
                    }

                    is Resource.SessionCompleted -> _parkingState.update { currentState ->
                        currentState.copy(
                            sessionCompleted = it.sessionIsCompleted
                        )
                    }
                }
            }
        }
    }


    private fun getUserBalance() {
        viewModelScope.launch {
            getBalance(getUserId()).collect {
                when (it) {
                    is Resource.Success -> _parkingState.update { currentState ->
                        currentState.copy(balance = it.data.toPresentation())
                    }

                    is Resource.Loading -> _parkingState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)

                    is Resource.SessionCompleted -> _parkingState.update { currentState ->
                        currentState.copy(
                            sessionCompleted = it.sessionIsCompleted
                        )
                    }

                }
            }
        }
    }

    private fun updateErrorMessage(message: String?) {

        _parkingState.update { currentState ->
            currentState.copy(
                errorMessage = message
            )
        }
    }

    sealed interface ParkingUiEvent {
        data class NavigateToTimer(
            val stationExternalId: String,
            val carId: Int,
            val startDate: String,
            val zone: String
        ) : ParkingUiEvent
    }

}