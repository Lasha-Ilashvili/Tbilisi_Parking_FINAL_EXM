package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet.balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetUserIdUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.balance.RememberCardUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.wallet.CardNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.wallet.CvvValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.wallet.DateValidatorUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.balance.BalanceEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.removeFormatting
import com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.balance.toDomain
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.balance.CardDetails
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.balance.BalanceState
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
class BalanceViewModel @Inject constructor(
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val cardNumberValidator: CardNumberValidatorUseCase,
    private val dateValidator: DateValidatorUseCase,
    private val cvvValidator: CvvValidatorUseCase,
    private val getUserId: GetUserIdUseCase,
    private val rememberCardUseCase: RememberCardUseCase
) : ViewModel() {

    private val _balanceState = MutableStateFlow(BalanceState())
    val balanceState get() = _balanceState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<BalanceUiEvent>()
    val uiEvent get() = _uiEvent.asSharedFlow()

    fun onEvent(event: BalanceEvent) = with(event) {
        when (this) {
            is BalanceEvent.SetButtonState -> setButtonState(fields = fields)

            is BalanceEvent.ProceedToPayment -> validateFields(
                cardNumber = cardNumber,
                date = date,
                cvv = cvv,
                isRememberCardChecked = isRememberCardChecked
            )

            BalanceEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun validateFields(
        cardNumber: TextInputLayout,
        date: TextInputLayout,
        cvv: TextInputLayout,
        isRememberCardChecked: Boolean
    ) {
        val cardNumberInput = cardNumber.editText?.text.toString().removeFormatting(" ")
        val dateInput = date.editText?.text.toString().removeFormatting("/")
        val cvvInput = cvv.editText?.text.toString()

        val isCardNumberValid = cardNumberValidator(cardNumberInput)
        val isDateValid = dateValidator(dateInput)
        val isCvvValid = cvvValidator(cvvInput)

        val areFieldsValid =
            listOf(isCardNumberValid, isDateValid, isCvvValid)
                .all { it }

        validateField(isCardNumberValid, cardNumber)
        validateField(isDateValid, date)
        validateField(isCvvValid, cvv)

        if (!areFieldsValid) {
            return
        }

        if (isRememberCardChecked) {
            rememberCard(
                cardNumber = cardNumberInput,
                date = date.editText?.text.toString(),
                cvv = cvvInput
            )
        }

        proceedToPayment()
    }

    private fun rememberCard(
        cardNumber: String,
        date: String,
        cvv: String
    ) {
        viewModelScope.launch {
            val cardDetails = CardDetails(
                userId = getUserId(),
                cardNumber = cardNumber,
                date = date,
                cvv = cvv
            )

            rememberCardUseCase(cardDetails.toDomain()).collect {
                when (it) {
                    is Resource.Error -> updateErrorMessage(it.errorMessage)

                    is Resource.Loading -> _balanceState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Success -> _uiEvent.emit(BalanceUiEvent.NavigateToMain)
                }
            }
        }
    }

    private fun validateField(isFieldValid: Boolean, textInputLayout: TextInputLayout) {
        updateErrorTextInputLayout(
            errorTextInputLayout = textInputLayout,
            isErrorEnabled = !isFieldValid
        )
    }

    private fun updateErrorTextInputLayout(
        errorTextInputLayout: TextInputLayout,
        isErrorEnabled: Boolean
    ) {
        _balanceState.update { currentState ->
            currentState.copy(
                errorTextInputLayout = errorTextInputLayout,
                isErrorEnabled = isErrorEnabled
            )
        }
    }

    private fun proceedToPayment() {
        println("Success")
    }

    private fun setButtonState(fields: List<TextInputLayout>) {
        _balanceState.update { currentState ->
            currentState.copy(isButtonEnabled = fieldsAreNotBlank(fields))
        }
    }

    private fun updateErrorMessage(message: String? = null) {
        _balanceState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    sealed interface BalanceUiEvent {
        data object NavigateToMain : BalanceUiEvent
    }
}