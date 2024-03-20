package com.example.tbilisi_parking_final_exm.presentation.screen.parking.vehicle_bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.parking.delete_vehicle.DeleteVehicleUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.vehicle_bottom_sheet.VehicleBottomSheetEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VehicleBottomSheetViewModel @Inject constructor(
    private val deleteVehicleUseCase: DeleteVehicleUseCase
) : ViewModel() {

    private val _vehicleBottomSheetUiState = (MutableSharedFlow<VehicleBottomSheetUiEvent>())
    val vehicleBottomSheetUiState get() = _vehicleBottomSheetUiState

    fun onEvent(event: VehicleBottomSheetEvent) {
        when (event) {
            is VehicleBottomSheetEvent.DeleteVehicle -> deleteVehicle(event.vehicleId)
        }
    }

    private fun deleteVehicle(vehicleId: Int) {
        viewModelScope.launch {
            deleteVehicleUseCase(vehicleId = vehicleId).collect {
                println("vehicle deleted")
                when(it) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        _vehicleBottomSheetUiState.emit(VehicleBottomSheetUiEvent.NavigateToParkingFragment)
                    }
                }
            }
        }

    }

    sealed interface VehicleBottomSheetUiEvent {
        data object NavigateToParkingFragment : VehicleBottomSheetUiEvent
    }
}