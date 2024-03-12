package com.example.tbilisi_parking_final_exm.presentation.screen.parking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.get_vehicles.GetAllVehicleUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.profile.GetProfileUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.ParkingEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.vehicle.toPresenter
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
    private val getProfile: GetProfileUseCase
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
//          get userId
            getProfile.invoke().collect {
                when (it) {
                    is Resource.Loading -> _parkingState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Error -> updateErrorMessage(it.errorMessage)

                    is Resource.Success -> getVehicles(
                        it.data.id
                    )
                }
            }
        }
    }

    private suspend fun getVehicles(userId: Int) {
        getAllVehicle(userId = userId).collect {
            println("this is parking viewModel -> $it")
            when (it) {
                is Resource.Loading -> _parkingState.update { currentState ->
                    currentState.copy(
                        isLoading = it.loading
                    )
                }

                is Resource.Error -> updateErrorMessage(it.errorMessage)

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

    private fun updateErrorMessage(message: String?) {
        _parkingState.update { currentState ->
            currentState.copy(
                errorMessage = message
            )
        }
    }


}