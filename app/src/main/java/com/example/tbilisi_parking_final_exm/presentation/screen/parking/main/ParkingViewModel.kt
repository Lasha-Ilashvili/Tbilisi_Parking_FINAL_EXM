package com.example.tbilisi_parking_final_exm.presentation.screen.parking.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetUserIdUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.parking.vehicle.get_vehicles.GetAllVehicleUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.balance.GetBalanceUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.ParkingEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.parking.vehicle.toPresenter
import com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.cards.toPresentation
import com.example.tbilisi_parking_final_exm.presentation.state.parking.ParkingState
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val getBalanceUseCase: GetBalanceUseCase
) : ViewModel() {

    private val _parkingState = MutableStateFlow(ParkingState())
    val parkingState: SharedFlow<ParkingState> = _parkingState.asStateFlow()

    fun onEvent(event: ParkingEvent) {
        when (event) {
            is ParkingEvent.FetchAllVehicle -> fetchAllVehicle()
            is ParkingEvent.ResetErrorMessage -> updateErrorMessage(message = null)
            is ParkingEvent.GetUserBalance -> getUserBalance()
        }
    }

    private fun fetchAllVehicle() {
        viewModelScope.launch {
            getAllVehicle(userId = getUserId()).collect {
                println(it)
                when (it) {
                    is Resource.Loading -> _parkingState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Error -> {
//                        complete session while Unauthorized access occurs (refreshToken expired)
                        if(it.errorMessage == "Unauthorized access")
                        updateErrorMessage(it.errorMessage)
                    }

                    is Resource.Success -> _parkingState.update { currentState ->
                        currentState.copy(
                            vehicles = it.data.map {
                                it.toPresenter()
                            }
                        )
                    }
                }
            }
        }
    }



    private fun getUserBalance() {
        viewModelScope.launch {
            getBalanceUseCase(getUserId()).collect {
                when (it) {
                    is Resource.Success -> _parkingState.update { currentState ->
                        currentState.copy(balance = it.data.toPresentation())
                    }

                    is Resource.Loading -> _parkingState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
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

}