package com.example.tbilisi_parking_final_exm.presentation.screen.parking.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetRefreshTokenUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetUserIdUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.SaveAccessTokenUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.refresh_token.RefreshTokenUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.vehicle.get_vehicles.GetAllVehicleUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.ParkingEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.vehicle.toPresenter
import com.example.tbilisi_parking_final_exm.presentation.state.parking.ParkingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ParkingViewModel @Inject constructor(
    private val getAllVehicle: GetAllVehicleUseCase,
    private val getUserId: GetUserIdUseCase,
    private val refreshToken: RefreshTokenUseCase,
    private val getRefreshToken: GetRefreshTokenUseCase,
    private val saveAccessToken: SaveAccessTokenUseCase
) : ViewModel() {

    private val _parkingState = MutableStateFlow(ParkingState())
    val parkingState: SharedFlow<ParkingState> = _parkingState.asStateFlow()

    fun onEvent(event: ParkingEvent) {
        when (event) {
            is ParkingEvent.FetchAllVehicle -> fetchAllVehicle()
            is ParkingEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun fetchAllVehicle() {
        viewModelScope.launch {
//            getNewToken()
            getAllVehicle(userId = getUserId().first().toInt()).collect {

                when (it) {
                    is Resource.Loading -> _parkingState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Error -> {
                        if (it.errorMessage == "Unauthorized access"){
                            getNewToken()
                            println("Unauthorized access")
                        }
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

    private suspend fun getNewToken(){
          val refreshToken = runBlocking { getRefreshToken().first() }
        refreshToken(refreshToken).collect{
            when(it){
                is Resource.Success -> {
                    saveAccessToken(it.data.accessToken)
                    fetchAllVehicle()
                    println("this is new token -> ${it.data.accessToken}")

                }

                is Resource.Error -> {
                    println("error occurred while refreshing token")
                    updateErrorMessage(it.errorMessage)
                }

                is Resource.Loading -> _parkingState.update { currentState ->
                    currentState.copy(
                        isLoading = it.loading
                    )
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