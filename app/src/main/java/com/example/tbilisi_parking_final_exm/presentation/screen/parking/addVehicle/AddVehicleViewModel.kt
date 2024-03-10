package com.example.tbilisi_parking_final_exm.presentation.screen.parking.addVehicle

import androidx.lifecycle.ViewModel
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.PlateNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.add_vehicle.AddVehicleEvent
import com.example.tbilisi_parking_final_exm.presentation.state.add_vehicle.AddVehicleState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddVehicleViewModel @Inject constructor(
    private val plateNumberValidator: PlateNumberValidatorUseCase,
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase
) : ViewModel() {
    private val _addVehicleState = MutableStateFlow(AddVehicleState())
    val addVehicleState: SharedFlow<AddVehicleState> = _addVehicleState.asStateFlow()

    fun onEvent(event: AddVehicleEvent) = with(event) {
        when (this) {
            is AddVehicleEvent.AddVehicle -> addVehicle(
                name = name,
                plateNumber = plateNumber
            )
            is AddVehicleEvent.SetButtonState -> setButtonState(fields = fields)
            is AddVehicleEvent.ResetErrorMessage -> resetErrorMessage(message = null)
        }
    }
    private fun addVehicle(name: TextInputLayout, plateNumber: TextInputLayout) {
        val plateNumberInput = plateNumber.editText?.text.toString().uppercase()

        val isPlateNumberValid = plateNumberValidator(plateNumberInput)

        updateErrorTextInputLayout(plateNumber, isPlateNumberValid)

        if(isPlateNumberValid ) {
            println("trigger add vehicle event")
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

    private fun resetErrorMessage(message: String?) {
        _addVehicleState.update { currentState ->
            currentState.copy(
                errorMessage = message
            )
        }
    }
}



