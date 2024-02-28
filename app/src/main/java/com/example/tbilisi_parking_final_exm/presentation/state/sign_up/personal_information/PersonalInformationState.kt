package com.example.tbilisi_parking_final_exm.presentation.state.sign_up.personal_information

import com.google.android.material.textfield.TextInputLayout


data class PersonalInformationState(
    val errorTextInputLayout: TextInputLayout? = null,
    val isErrorEnabled: Boolean = false,
    val isButtonEnabled: Boolean = false
)