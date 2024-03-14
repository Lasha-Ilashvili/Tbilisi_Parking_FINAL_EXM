package com.example.tbilisi_parking_final_exm.presentation.event.cards.buy_card

import com.google.android.material.textfield.TextInputLayout


sealed class BuyCardEvent {
    data class SetButtonState(val fields: List<TextInputLayout>) : BuyCardEvent()
}