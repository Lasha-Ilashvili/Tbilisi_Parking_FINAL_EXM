package com.example.tbilisi_parking_final_exm.presentation.screen.licenses.buy_license

import androidx.lifecycle.ViewModel
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.PlateNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.PersonalNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.licenses.buy_license.BuyLicenseEvent
import com.example.tbilisi_parking_final_exm.presentation.state.licenses.buy_license.BuyLicenseState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class BuyLicenseViewModel @Inject constructor(
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val plateNumberValidator: PlateNumberValidatorUseCase,
    private val personalNumberValidator: PersonalNumberValidatorUseCase
) : ViewModel() {

    private val _buyLicenseState = MutableStateFlow(BuyLicenseState())
    val buyLicenseState get() = _buyLicenseState.asStateFlow()

    fun onEvent(event: BuyLicenseEvent) = with(event) {
        when (this) {
            is BuyLicenseEvent.SetButtonState -> setButtonState(fields = fields)
        }
    }

    private fun validateFields(
        plateNumber: TextInputLayout,
        personalNumber: TextInputLayout
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
}