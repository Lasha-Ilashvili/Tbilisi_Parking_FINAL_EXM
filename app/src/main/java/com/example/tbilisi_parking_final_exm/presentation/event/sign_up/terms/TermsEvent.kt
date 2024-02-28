package com.example.tbilisi_parking_final_exm.presentation.event.sign_up.terms


sealed class TermsEvent {
    data class SetButtonState(val isChecked: Boolean) : TermsEvent()
}