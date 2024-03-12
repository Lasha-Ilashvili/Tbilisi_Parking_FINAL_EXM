package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet

import androidx.lifecycle.ViewModel
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.wallet.CardNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.wallet.CvvValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.wallet.DateValidatorUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.WalletEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.removeFormatting
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.WalletState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class WalletViewModel @Inject constructor(
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val cardNumberValidator: CardNumberValidatorUseCase,
    private val dateValidator: DateValidatorUseCase,
    private val cvvValidator: CvvValidatorUseCase
) : ViewModel() {

    private val _walletState = MutableStateFlow(WalletState())
    val walletState get() = _walletState.asStateFlow()

    fun onEvent(event: WalletEvent) = with(event) {
        when (this) {
            is WalletEvent.SetPayNowButtonState -> setPayNowButtonState(field = field)

            is WalletEvent.SetCardLayoutState -> setCardLayoutState(isCardLayoutEnabled = isCardLayoutEnabled)

            is WalletEvent.SetProceedToPaymentButtonState -> setProceedToPaymentButtonState(fields = fields)

            is WalletEvent.ProceedToPayment -> validateFields(
                cardNumber = cardNumber,
                date = date,
                cvv = cvv
            )
        }
    }

    private fun validateFields(
        cardNumber: TextInputLayout,
        date: TextInputLayout,
        cvv: TextInputLayout
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

        proceedToPayment()
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
        _walletState.update { currentState ->
            currentState.copy(
                errorTextInputLayout = errorTextInputLayout,
                isErrorEnabled = isErrorEnabled
            )
        }
    }

    private fun proceedToPayment() {
        println("Success")
    }

    private fun setPayNowButtonState(field: TextInputLayout) {
        _walletState.update { currentState ->
            currentState.copy(isPayNowButtonEnabled = fieldsAreNotBlank(listOf(field)))
        }
    }

    private fun setCardLayoutState(isCardLayoutEnabled: Boolean) {
        _walletState.update { currentState ->
            currentState.copy(isCardLayoutEnabled = isCardLayoutEnabled)
        }
    }

    private fun setProceedToPaymentButtonState(fields: List<TextInputLayout>) {
        _walletState.update { currentState ->
            currentState.copy(isProceedToPaymentButtonEnabled = fieldsAreNotBlank(fields))
        }
    }
}