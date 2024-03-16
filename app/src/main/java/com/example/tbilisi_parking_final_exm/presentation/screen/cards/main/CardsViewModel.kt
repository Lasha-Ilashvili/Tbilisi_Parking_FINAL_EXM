package com.example.tbilisi_parking_final_exm.presentation.screen.cards.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            getCardsUseCase().collect {
                _cardsState.update { currentState ->
                    currentState.copy(data = it.map { getCard ->
                        getCard.toPresentation()
                    })
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
    }

    private fun updateErrorMessage(message: String? = null) {
        _cardsState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }
}