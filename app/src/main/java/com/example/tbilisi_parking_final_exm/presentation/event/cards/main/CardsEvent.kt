package com.example.tbilisi_parking_final_exm.presentation.event.cards.main


sealed class CardsEvent {
    data object GetCards : CardsEvent()
    data object ResetErrorMessage : CardsEvent()
}