package com.example.tbilisi_parking_final_exm.presentation.screen.license_cards.buy_license

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.vehicle.vehicle.GetVehicle
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetUserIdUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.license_cards.buy_license.BuyLicenseUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.PlateNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.PersonalNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.vehicle.get_vehicles.GetAllVehicleUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.license_cards.buy_license.BuyLicenseEvent
import com.example.tbilisi_parking_final_exm.presentation.state.license_cards.buy_license.BuyLicenseState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BuyLicenseViewModel @Inject constructor(
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val plateNumberValidator: PlateNumberValidatorUseCase,
    private val personalNumberValidator: PersonalNumberValidatorUseCase,
    private val getUserId: GetUserIdUseCase,
    private val getAllVehiclesUseCase: GetAllVehicleUseCase,
    private val buyLicenseUseCase: BuyLicenseUseCase
) : ViewModel() {

    private val _buyLicenseState = MutableStateFlow(BuyLicenseState())
    val buyLicenseState get() = _buyLicenseState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<BuyLicenseUiEvent>()
    val uiEvent get() = _uiEvent.asSharedFlow()

    fun onEvent(event: BuyLicenseEvent) = with(event) {
        when (this) {
            BuyLicenseEvent.ResetErrorMessage -> updateErrorMessage()

            is BuyLicenseEvent.SetButtonState -> setButtonState(fields = fields)

            is BuyLicenseEvent.BuyLicense -> validateFields(
                plateNumber = plateNumber,
                personalNumber = personalNumber,
                descriptionId = descriptionId
            )
        }
    }

    private fun validateFields(
        plateNumber: TextInputLayout,
        personalNumber: TextInputLayout,
        descriptionId: Int
    ) {
        val plateNumberInput = plateNumber.editText?.text.toString()
        val personalNumberInput = personalNumber.editText?.text.toString()

        val isPlateNumberValid = plateNumberValidator(plateNumberInput)
        val isPersonalNumberValid = personalNumberValidator(personalNumberInput)

        val areFieldsValid = listOf(isPlateNumberValid, isPersonalNumberValid).all { it }

        updateErrorTextInputLayout(isPlateNumberValid, plateNumber)
        updateErrorTextInputLayout(isPersonalNumberValid, personalNumber)

        if (!areFieldsValid) {
            return
        }

        getAllVehicles(plateNumberInput, descriptionId)
    }

    private fun getAllVehicles(
        plateNumber: String,
        descriptionId: Int
    ) {
        viewModelScope.launch {
            getAllVehiclesUseCase(userId = getUserId()).collect {
                when (it) {
                    is Resource.Loading -> _buyLicenseState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)


                    is Resource.Success -> buyLicense(
                        vehicles = it.data,
                        plateNumber = plateNumber,
                        descriptionId = descriptionId
                    )
                }
            }
        }
    }

    private fun buyLicense(vehicles: List<GetVehicle>, plateNumber: String, descriptionId: Int) {
        viewModelScope.launch {
            buyLicenseUseCase.invoke(
                vehicles = vehicles,
                plateNumber = plateNumber,
                descriptionId = descriptionId,
                userId = getUserId()
            ).collect {
                when (it) {
                    is Resource.Loading -> _buyLicenseState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> {
                        updateErrorMessage(message = it.errorMessage)
                    }

                    is Resource.Success -> _uiEvent.emit(BuyLicenseUiEvent.NavigateToLicenses)
                }
            }
        }
    }

    private fun updateErrorTextInputLayout(
        isFieldValid: Boolean,
        errorTextInputLayout: TextInputLayout
    ) {
        _buyLicenseState.update { currentState ->
            currentState.copy(
                errorTextInputLayout = errorTextInputLayout,
                isErrorEnabled = !isFieldValid
            )
        }
    }

    private fun setButtonState(fields: List<TextInputLayout>) {
        _buyLicenseState.update { currentState ->
            currentState.copy(isButtonEnabled = fieldsAreNotBlank(fields))
        }
    }

    private fun updateErrorMessage(message: String? = null) {
        _buyLicenseState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    sealed interface BuyLicenseUiEvent {
        data object NavigateToLicenses : BuyLicenseUiEvent
    }
}