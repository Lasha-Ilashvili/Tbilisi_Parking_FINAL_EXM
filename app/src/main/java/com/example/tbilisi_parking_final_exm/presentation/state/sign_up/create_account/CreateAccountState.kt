package com.example.tbilisi_parking_final_exm.presentation.state.sign_up.create_account

import com.example.tbilisi_parking_final_exm.R
import com.google.android.material.textfield.TextInputLayout


data class CreateAccountState(
    val isLoading: Boolean = false,
    val errorTextInputLayout: TextInputLayout? = null,
    val isErrorEnabled: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val errorMessage: String? = null,
    val inputErrorMessage:Int = R.string.invalid_input
)