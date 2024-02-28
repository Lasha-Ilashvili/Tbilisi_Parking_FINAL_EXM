package com.example.tbilisi_parking_final_exm.presentation.screen.sign_up.personal_information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.MobileNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.NameValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.PersonalNumberValidatorUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.sign_up.personal_information.PersonalInformationEvent
import com.example.tbilisi_parking_final_exm.presentation.state.sign_up.personal_information.PersonalInformationState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PersonalInformationViewModel @Inject constructor(
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val personalNumberValidator: PersonalNumberValidatorUseCase,
    private val nameValidator: NameValidatorUseCase,
    private val mobileNumberValidator: MobileNumberValidatorUseCase
) : ViewModel() {

    private val _personalInformationState = MutableStateFlow(PersonalInformationState())
    val personalInformationState get() = _personalInformationState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PersonalInformationUiEvent>()
    val uiEvent get() = _uiEvent

    fun onEvent(event: PersonalInformationEvent) = with(event) {
        when (this) {
            is PersonalInformationEvent.SetButtonState -> setButtonState(fields = fields)

            is PersonalInformationEvent.ProceedToCreateAccount -> {
                validateFields(
                    personalNumber = personalNumber,
                    firstName = firstName,
                    lastName = lastName,
                    mobileNumber = mobileNumber
                )
            }
        }
    }

    private fun validateFields(
        personalNumber: TextInputLayout,
        firstName: TextInputLayout,
        lastName: TextInputLayout,
        mobileNumber: TextInputLayout
    ) {
        val personalNumberInput = personalNumber.editText?.text.toString()
        val firstNameInput = firstName.editText?.text.toString()
        val lastNameInput = lastName.editText?.text.toString()
        val mobileNumberInput = mobileNumber.editText?.text.toString()

        val isPersonalNumberValid = personalNumberValidator(personalNumberInput)
        val isFirstNameValid = nameValidator(firstNameInput)
        val isLastNameValid = nameValidator(lastNameInput)
        val isMobileNumberValid = mobileNumberValidator(mobileNumberInput)

        val areFieldsValid =
            listOf(isPersonalNumberValid, isFirstNameValid, isLastNameValid, isMobileNumberValid)
                .all { it }

        validateField(isPersonalNumberValid, personalNumber)
        validateField(isFirstNameValid, firstName)
        validateField(isLastNameValid, lastName)
        validateField(isMobileNumberValid, mobileNumber)

        if (!areFieldsValid) {
            return
        }

        proceedToCreateAccount(
            personalNumber = personalNumberInput,
            firstName = firstNameInput,
            lastName = lastNameInput,
            mobileNumber = mobileNumberInput
        )
    }

    private fun validateField(isFieldValid: Boolean, textInputLayout: TextInputLayout) {
        updateErrorTextInputLayout(
            errorTextInputLayout = textInputLayout,
            isErrorEnabled = !isFieldValid
        )
    }

    private fun proceedToCreateAccount(
        personalNumber: String,
        firstName: String,
        lastName: String,
        mobileNumber: String
    ) {
        viewModelScope.launch {
            _uiEvent.emit(
                PersonalInformationUiEvent.NavigateToCreateAccount(
                    personalNumber = personalNumber,
                    firstName = firstName,
                    lastName = lastName,
                    mobileNumber = mobileNumber
                )
            )
        }
    }

    private fun updateErrorTextInputLayout(
        errorTextInputLayout: TextInputLayout,
        isErrorEnabled: Boolean
    ) {
        _personalInformationState.update { currentState ->
            currentState.copy(
                errorTextInputLayout = errorTextInputLayout,
                isErrorEnabled = isErrorEnabled
            )
        }
    }

    private fun setButtonState(fields: List<TextInputLayout>) {
        _personalInformationState.update { currentState ->
            currentState.copy(isButtonEnabled = fieldsAreNotBlank(fields))
        }
    }

    sealed interface PersonalInformationUiEvent {
        data class NavigateToCreateAccount(
            val personalNumber: String,
            val firstName: String,
            val lastName: String,
            val mobileNumber: String
        ) : PersonalInformationUiEvent
    }
}