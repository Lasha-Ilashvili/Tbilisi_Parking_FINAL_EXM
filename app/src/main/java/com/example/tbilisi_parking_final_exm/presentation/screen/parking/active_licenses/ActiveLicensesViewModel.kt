package com.example.tbilisi_parking_final_exm.presentation.screen.parking.active_licenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.active_licenses.GetActiveLicensesUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.active_licenses.ActiveLicensesEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.active_licenses.toPresentation
import com.example.tbilisi_parking_final_exm.presentation.state.parking.active_licenses.ActiveLicensesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ActiveLicensesViewModel @Inject constructor(
    private val activeLicensesUseCase: GetActiveLicensesUseCase
) : ViewModel() {

    private val _activeLicensesState = MutableStateFlow(ActiveLicensesState())
    val activeLicensesState get() = _activeLicensesState.asStateFlow()

    fun onEvent(event: ActiveLicensesEvent) = with(event) {
        when (this) {
            is ActiveLicensesEvent.GetActiveLicenses -> getCards(carId = carId)
            ActiveLicensesEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun getCards(carId: Int) {
        viewModelScope.launch {
            activeLicensesUseCase(carId).collect {
                when (it) {
                    is Resource.Loading -> _activeLicensesState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)

                    is Resource.Success -> _activeLicensesState.update { currentState ->
                        currentState.copy(data = it.data.map { getActiveLicense ->
                            getActiveLicense.toPresentation()
                        })
                    }
                }
            }
        }
    }

    private fun updateErrorMessage(message: String? = null) {
        _activeLicensesState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }
}