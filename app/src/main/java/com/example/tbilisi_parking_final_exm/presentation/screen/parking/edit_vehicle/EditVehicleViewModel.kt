package com.example.tbilisi_parking_final_exm.presentation.screen.parking.edit_vehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.parking.vehicle.edit_vehicle.EditVehicleUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.edit_vehicle.EditVehicleEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.parking.vehicle.toDomain
import com.example.tbilisi_parking_final_exm.presentation.model.parking.vehicle.edit_vehicle.EditVehicle
import com.example.tbilisi_parking_final_exm.presentation.state.parking.edit_vehicle.EditVehicleState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditVehicleViewModel @Inject constructor(
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val editVehicleUseCase: EditVehicleUseCase
): ViewModel() {

    private val _editVehicleState = MutableStateFlow(EditVehicleState())
    val editVehicleState: SharedFlow<EditVehicleState> = _editVehicleState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<EditVehicleUiEvent>()
    val uiEvent get() = _uiEvent

    fun onEvent(event: EditVehicleEvent) = with(event) {
        when (this) {
            is EditVehicleEvent.EditVehicle -> editVehicle(vehicleId = vehicleId, name = name)
            is EditVehicleEvent.SetButtonState -> setButtonState(fields = fields)
        }
    }

    private fun editVehicle(vehicleId: Int, name: String){
        viewModelScope.launch {
            val car = EditVehicle(carId = vehicleId, name = name).toDomain()
            editVehicleUseCase(car).collect{
                when (it) {
                    is Resource.Success -> {

                        _uiEvent.emit(EditVehicleUiEvent.NavigateToParkingFragment)

                    }

                    is Resource.Error -> updateErrorMessage(it.errorMessage)

                    is Resource.Loading -> _editVehicleState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }
                }
            }

        }
    }
    private fun setButtonState(fields:  List<TextInputLayout>) {
        _editVehicleState.update { currentState ->
            currentState.copy(
                isButtonEnabled = fieldsAreNotBlank(fields)
            )
        }
    }

    private fun updateErrorMessage(message: String?) {
        _editVehicleState.update { currentState ->
            currentState.copy(
                errorMessage = message
            )
        }
    }

    sealed interface EditVehicleUiEvent{
        data object NavigateToParkingFragment :EditVehicleUiEvent

    }
}