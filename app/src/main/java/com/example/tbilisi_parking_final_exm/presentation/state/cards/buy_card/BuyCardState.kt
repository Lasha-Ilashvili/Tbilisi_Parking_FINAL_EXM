package com.example.tbilisi_parking_final_exm.presentation.state.cards.buy_card

import com.google.android.material.textfield.TextInputLayout


data class BuyCardState(
    val errorTextInputLayout: TextInputLayout? = null,
    val isErrorEnabled: Boolean = false,
    val isButtonEnabled: Boolean = false
)