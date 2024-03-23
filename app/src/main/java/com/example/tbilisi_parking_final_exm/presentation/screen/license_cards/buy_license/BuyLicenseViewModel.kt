package com.example.tbilisi_parking_final_exm.presentation.screen.license_cards.buy_license

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.parking.vehicle.vehicle.GetVehicle
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.cards.GetCardDetails
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetUserIdUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.license_cards.buy_license.BuyLicenseUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.parking.vehicle.get_vehicles.GetAllVehicleUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.PlateNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.PersonalNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.wallet.CardNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.wallet.CvvValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.wallet.DateValidatorUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.license_cards.buy_license.BuyLicenseEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.removeFormatting
import com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.balance.toDomain
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.cards.CardDetails
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
    private val cardNumberValidator: CardNumberValidatorUseCase,
    private val dateValidator: DateValidatorUseCase,
    private val cvvValidator: CvvValidatorUseCase,
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
                descriptionId = descriptionId,
                cardNumber = cardNumber,
                date = date,
                cvv = cvv
            )
        }
    }

    private fun validateFields(
        plateNumber: TextInputLayout,
        personalNumber: TextInputLayout,
        descriptionId: Int,
        cardNumber: TextInputLayout?,
        date: TextInputLayout?,
        cvv: TextInputLayout?
    ) {
        val dateInput = date?.editText?.text?.toString()

        val plateNumberInput = plateNumber.editText?.text.toString()
        val personalNumberInput = personalNumber.editText?.text.toString()
        val formattedCardNumber = cardNumber?.editText?.text?.toString()?.removeFormatting(" ")
        val formattedDate = date?.editText?.text?.toString()?.removeFormatting("/")
        val formattedCvv = cvv?.editText?.text?.toString()

        val isPlateNumberValid = plateNumberValidator(plateNumberInput)
        val isPersonalNumberValid = personalNumberValidator(personalNumberInput)
        val isCardNumberValid = formattedCardNumber?.let { cardNumberValidator(it) } ?: true
        val isDateValid = formattedDate?.let { dateValidator(it) } ?: true
        val isCvvValid = formattedCvv?.let { cvvValidator(it) } ?: true

        val areFieldsValid = listOf(
            isPlateNumberValid, isPersonalNumberValid, isCardNumberValid, isDateValid, isCvvValid
        ).all { it }

        updateErrorTextInputLayout(isPlateNumberValid, plateNumber)
        updateErrorTextInputLayout(isPersonalNumberValid, personalNumber)
        updateErrorTextInputLayout(isCardNumberValid, cardNumber)
        updateErrorTextInputLayout(isDateValid, date)
        updateErrorTextInputLayout(isCvvValid, cvv)

        if (!areFieldsValid) {
            return
        }

        getAllVehicles(
            plateNumberInput, descriptionId, formattedCardNumber, dateInput, formattedCvv
        )
    }

    private fun getAllVehicles(
        plateNumber: String,
        descriptionId: Int,
        cardNumber: String?,
        date: String?,
        cvv: String?
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
                        descriptionId = descriptionId,
                        cardNumber = cardNumber,
                        date = date,
                        cvv = cvv
                    )

                    is Resource.SessionCompleted -> _buyLicenseState.update {currentState ->
                        currentState.copy(
                            sessionCompleted = it.sessionIsCompleted
                        )
                    }

                }
            }
        }
    }

    private fun buyLicense(
        vehicles: List<GetVehicle>,
        plateNumber: String,
        descriptionId: Int,
        cardNumber: String?,
        date: String?,
        cvv: String?
    ) {
        viewModelScope.launch {
            buyLicenseUseCase.invoke(
                vehicles = vehicles,
                plateNumber = plateNumber,
                descriptionId = descriptionId,
                userId = getUserId(),
                getCardDetails = getCardDetails(getUserId(), cardNumber, date, cvv)
            ).collect {
                when (it) {
                    is Resource.Loading -> _buyLicenseState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> {
                        updateErrorMessage(message = it.errorMessage)
                    }

                    is Resource.Success -> _uiEvent.emit(BuyLicenseUiEvent.NavigateToLicenses)

                    is Resource.SessionCompleted -> _buyLicenseState.update {currentState ->
                        currentState.copy(
                            sessionCompleted = it.sessionIsCompleted
                        )
                    }

                }
            }
        }
    }

    private fun getCardDetails(
        userId: Int,
        cardNumber: String?,
        date: String?,
        cvv: String?
    ): GetCardDetails? {
        val validInput = listOf(userId, cardNumber, date, cvv).all {
            it != null
        }

        if (!validInput) {
            return null
        }

        return CardDetails(
            userId = userId,
            cardNumber = cardNumber!!,
            date = date!!,
            cvv = cvv!!
        ).toDomain()
    }

    private fun updateErrorTextInputLayout(
        isFieldValid: Boolean,
        errorTextInputLayout: TextInputLayout?
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