package com.example.tbilisi_parking_final_exm.presentation.screen.license_cards.all_licenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.license_cards.all_licenses.GetLicensesUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.license_cards.all_licenses.LicensesEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.license_cards.toPresentation
import com.example.tbilisi_parking_final_exm.presentation.state.license_cards.all_licenses.LicensesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LicensesViewModel @Inject constructor(
    private val getLicensesUseCase: GetLicensesUseCase
) : ViewModel() {

    private val _licensesState = MutableStateFlow(LicensesState())
    val licensesState get() = _licensesState.asStateFlow()

    fun onEvent(event: LicensesEvent) {
        when (event) {
            is LicensesEvent.GetLicenses -> getCards()
            LicensesEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun getCards() {
        viewModelScope.launch {
            getLicensesUseCase().collect {
                when (it) {
                    is Resource.Loading -> _licensesState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> {
                        updateErrorMessage(message = it.errorMessage)
                    }

                    is Resource.Success -> _licensesState.update { currentState ->
                        currentState.copy(data = it.data.map { getLicense ->
                            getLicense.toPresentation()
                        })
                    }
                    else -> {}

                }
            }
        }
    }

    private fun updateErrorMessage(message: String? = null) {
        _licensesState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }
}