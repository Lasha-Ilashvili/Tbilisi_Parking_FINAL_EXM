package com.example.tbilisi_parking_final_exm.presentation.screen.cards.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.domain.model.cards.GetCard
import com.example.tbilisi_parking_final_exm.domain.usecase.cards.GetCardsUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.cards.main.CardsEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.cards.toPresentation
import com.example.tbilisi_parking_final_exm.presentation.state.cards.main.CardsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
            CardsEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun getCards() {
        viewModelScope.launch {

                _cardsState.update { currentState ->
                    currentState.copy(
                        data = cards.map {
                            it.toPresentation()
                        }
                    )
                }

//                when (it) {
//                    is Resource.Loading -> _cardsState.update { currentState ->
//                        currentState.copy(isLoading = it.loading)
//                    }
//
//                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
//
//                    is Resource.Success -> {
//                        _cardsState.update { currentState ->
//                            currentState.copy(data = it.data.map { getCard ->
//                                getCard.toPresentation()
//                            })
//                        }
//                    }
            }

    }

    private fun updateErrorMessage(message: String? = null) {
        _cardsState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    val cards = listOf(
        GetCard(
            title = "buy card"
        ),
        GetCard(
            title = "one day",
            zonalCard = GetCard.GetZonalCard(
                period = "1 day",
                price = (20),
                backgroundColor = "#219BCC"
            )
        ),
        GetCard(
            title = "one week",
            zonalCard = GetCard.GetZonalCard(
                period = "1 week",
                price = 100,
                backgroundColor = "#03DAC6"
            )
        ),
        GetCard(
            title = "one month",
            zonalCard = GetCard.GetZonalCard(
                period = "1 month",
                price = 300,
                backgroundColor = "#605A7C"
            )
        ),
        GetCard(
            title = "six months", zonalCard = GetCard.GetZonalCard(
                period = "6 months",
                price = 500,
                backgroundColor = "#E38568"
            )
        ),
        GetCard(
            title = "one year", zonalCard = GetCard.GetZonalCard(
                period = "1 year",
                price = 800,
                backgroundColor = "#68E387"
            )
        )
    )
}