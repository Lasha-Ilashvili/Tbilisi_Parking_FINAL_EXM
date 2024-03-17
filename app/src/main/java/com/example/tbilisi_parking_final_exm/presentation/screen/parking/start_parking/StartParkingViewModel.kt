package com.example.tbilisi_parking_final_exm.presentation.screen.parking.start_parking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetUserIdUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.user_panel.wallet.main.GetBalanceUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking.StartParkingEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.main.toPresentation
import com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking.StartParkingState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StartParkingViewModel @Inject constructor(
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val getUserId: GetUserIdUseCase,
    private val getBalanceUseCase: GetBalanceUseCase
) : ViewModel() {

    private val _startParkingState = MutableStateFlow(StartParkingState())
    val startParkingState get() = _startParkingState.asStateFlow()

    fun onEvent(event: StartParkingEvent) = with(event) {
        when (this) {
            is StartParkingEvent.SetButtonState -> setButtonState(field = field)

            StartParkingEvent.GetBalance -> getBalance()

            is StartParkingEvent.SetCostLayoutState -> setCostLayoutState(isCostLayoutEnabled = isCostLayoutEnabled)

            is StartParkingEvent.SetZoneState -> setZoneState(zone = zone)

            StartParkingEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun getBalance(){
        viewModelScope.launch {
            getBalanceUseCase(getUserId()).collect{
                when (it) {
                    is Resource.Success -> _startParkingState.update { currentState ->
                        currentState.copy(balance = it.data.toPresentation())
                    }

                    is Resource.Loading -> _startParkingState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun setZoneState(zone: StartParkingFragment.Zone) {
        _startParkingState.update { currentState ->
            currentState.copy(zone = zone)
        }
    }

    private fun setButtonState(field: TextInputLayout) {
        _startParkingState.update { currentState ->
            currentState.copy(isButtonEnabled = fieldsAreNotBlank(listOf(field)))
        }
    }

    private fun setCostLayoutState(isCostLayoutEnabled: Boolean) {
        _startParkingState.update { currentState ->
            currentState.copy(isCostLayoutEnabled = isCostLayoutEnabled)
        }
    }

    private fun updateErrorMessage(message: String? = null) {
        _startParkingState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }
}