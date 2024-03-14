package com.example.tbilisi_parking_final_exm.presentation.state.cards

import com.example.tbilisi_parking_final_exm.presentation.model.cards.Card


data class CardsState(
    val isLoading: Boolean = false,
    val data: List<Card>? = null,
    val errorMessage: String? = null
)