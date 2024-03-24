package com.example.tbilisi_parking_final_exm.presentation.screen.parking.vehicle_bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.parking.delete_vehicle.DeleteVehicleUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.vehicle_bottom_sheet.VehicleBottomSheetEvent
import com.example.tbilisi_parking_final_exm.presentation.state.parking.vehicle_bottom_sheet.VehicleBottomSheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VehicleBottomSheetViewModel @Inject constructor(
    private val deleteVehicleUseCase: DeleteVehicleUseCase
) : ViewModel() {

    private val _vehicleBottomSheetState =  MutableStateFlow(VehicleBottomSheetState())
    val vehicleBottomSheetState: SharedFlow<VehicleBottomSheetState> = _vehicleBottomSheetState.asStateFlow()

    private val _vehicleBottomSheetUiState = MutableSharedFlow<VehicleBottomSheetUiEvent>()
    val vehicleBottomSheetUiState get() = _vehicleBottomSheetUiState

    fun onEvent(event: VehicleBottomSheetEvent) {
        when (event) {
            is VehicleBottomSheetEvent.DeleteVehicle -> deleteVehicle(event.vehicleId)
            is VehicleBottomSheetEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun deleteVehicle(vehicleId: Int) {
        viewModelScope.launch {
            deleteVehicleUseCase(vehicleId = vehicleId).collect {
                println("vehicle deleted")
                when(it) {
                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        _vehicleBottomSheetUiState.emit(VehicleBottomSheetUiEvent.NavigateToParkingFragment)
                    }
                    else -> {}

                }
            }
        }

    }

    private fun updateErrorMessage(message: String? = null) {
        _vehicleBottomSheetState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    sealed interface VehicleBottomSheetUiEvent {
        data object NavigateToParkingFragment : VehicleBottomSheetUiEvent
    }
}