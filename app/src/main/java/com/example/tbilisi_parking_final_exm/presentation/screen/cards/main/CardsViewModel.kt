package com.example.tbilisi_parking_final_exm.presentation.screen.cards.main

import androidx.lifecycle.ViewModel
import com.example.tbilisi_parking_final_exm.domain.usecase.cards.GetCardsUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.cards.main.CardsEvent
import com.example.tbilisi_parking_final_exm.presentation.state.cards.main.CardsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase
) : ViewModel() {

    private val _cardsState = MutableStateFlow(CardsState())
    val cardsState get() = _cardsState.asStateFlow()

    fun onEvent(event: CardsEvent) {
        when (event) {
            is CardsEvent.GetCards -> getCards()
            CardsEvent.ResetErrorMessage -> {}
        }
    }

    private fun getCards() {
        _cardsState.update { currentState ->
            currentState.copy(data = getCardsUseCase())
        }
    }
}