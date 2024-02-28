package com.example.tbilisi_parking_final_exm.presentation.event.sign_up.personal_information

import com.google.android.material.textfield.TextInputLayout


sealed class PersonalInformationEvent {
    data class SetButtonState(val fields: List<TextInputLayout>) : PersonalInformationEvent()
    data class ProceedToCreateAccount(
        val personalNumber: TextInputLayout,
        val firstName: TextInputLayout,
        val lastName: TextInputLayout,
        val mobileNumber: TextInputLayout
    ) : PersonalInformationEvent()
}