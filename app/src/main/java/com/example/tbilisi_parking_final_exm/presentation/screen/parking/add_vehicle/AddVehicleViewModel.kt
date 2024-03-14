package com.example.tbilisi_parking_final_exm.presentation.screen.parking.add_vehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.vehicle.add_vehicle.AddVehicleUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetUserIdUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.PlateNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.add_vehicle.AddVehicleEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.vehicle.toDomain
import com.example.tbilisi_parking_final_exm.presentation.model.vehicle.add_vehicle.AddVehicle
import com.example.tbilisi_parking_final_exm.presentation.state.parking.add_vehicle.AddVehicleState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddVehicleViewModel @Inject constructor(
    private val plateNumberValidator: PlateNumberValidatorUseCase,
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val addVehicleUseCase: AddVehicleUseCase,
    private val getUserId: GetUserIdUseCase
) : ViewModel() {

    private val _addVehicleState = MutableStateFlow(AddVehicleState())
    val addVehicleState: SharedFlow<AddVehicleState> = _addVehicleState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<AddVehicleUiEvent>()
    val uiEvent: SharedFlow<AddVehicleUiEvent> get() = _uiEvent

    fun onEvent(event: AddVehicleEvent) = with(event) {
        when (this) {
            is AddVehicleEvent.AddVehicle -> validateForms(
                name = name,
                plateNumber = plateNumber
            )

            is AddVehicleEvent.SetButtonState -> setButtonState(fields = fields)
            is AddVehicleEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun validateForms(name: TextInputLayout, plateNumber: TextInputLayout) {
        val plateNumberInput = plateNumber.editText?.text.toString().uppercase()
        val nameInput = name.editText?.text.toString()

        val isPlateNumberValid = plateNumberValidator(plateNumberInput)

        updateErrorTextInputLayout(plateNumber, isPlateNumberValid)

        if (isPlateNumberValid) {
            registerVehicle(nameInput, plateNumberInput)

        }

    }

    private fun registerVehicle(name: String, plateNumber: String) {
        viewModelScope.launch {
            val vehicle = AddVehicle(userId = getUserId().first().toInt(), name = name, plateNumber = plateNumber)
            addVehicleUseCase(vehicle.toDomain()).collect{
                when (it) {
                    is Resource.Loading -> _addVehicleState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Error -> updateErrorMessage(it.errorMessage)

                    is Resource.Success -> _uiEvent.emit(AddVehicleUiEvent.NavigateToParking)
                }
            }
        }
    }

    private fun setButtonState(fields: List<TextInputLayout>) {
        _addVehicleState.update { currentState ->
            currentState.copy(
                isButtonEnabled = fieldsAreNotBlank(fields)
            )
        }
    }

    private fun updateErrorTextInputLayout(
        errorTextInputLayout: TextInputLayout,
        isFieldValid: Boolean
    ) {
        _addVehicleState.update { currentState ->
            currentState.copy(
                errorTextInputLayout = errorTextInputLayout,
                isErrorEnabled = !isFieldValid
            )
        }
    }

    private fun updateErrorMessage(message: String?) {
        _addVehicleState.update { currentState ->
            currentState.copy(
                errorMessage = message
            )
        }
    }

    sealed interface AddVehicleUiEvent{
        data object NavigateToParking : AddVehicleUiEvent
    }

}



