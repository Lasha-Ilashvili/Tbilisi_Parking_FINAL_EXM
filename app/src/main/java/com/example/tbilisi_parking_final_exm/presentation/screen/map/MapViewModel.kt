package com.example.tbilisi_parking_final_exm.presentation.screen.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.map.MarkerLocationsUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.map.MapEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.map.toPresentation
import com.example.tbilisi_parking_final_exm.presentation.state.map.MapState
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val markerLocationsUseCase: MarkerLocationsUseCase
) : ViewModel() {

    private val _mapState = MutableStateFlow(MapState())
    val mapState get() = _mapState.asStateFlow()

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.SetMarkers -> setMarkers(event.jsonString)
            is MapEvent.SetUserLocation -> updateUserLocation(userLatLng = event.userLatLng)
            is MapEvent.UpdateUserLocation -> updateUserLocation(event.shouldShowUserLocation)
            MapEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun setMarkers(jsonString: String) {
        viewModelScope.launch {
            markerLocationsUseCase(jsonString).collect {
                when (it) {
                    is Resource.Success -> {
                        _mapState.update { currentState ->
                            currentState.copy(markerLocation = it.data.map { getMarkerLocation ->
                                getMarkerLocation.toPresentation()
                            })
                        }
                    }

                    is Resource.Loading -> _mapState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                    else -> {}
                }
            }
        }
    }

    private fun updateErrorMessage(message: String? = null) {
        _mapState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    private fun updateUserLocation(
        shouldShowUserLocation: Boolean = true,
        userLatLng: LatLng? = null
    ) {
        _mapState.update { currentState ->
            currentState.copy(
                userLatLng = userLatLng,
                shouldShowUserLocation = shouldShowUserLocation
            )
        }
    }
}