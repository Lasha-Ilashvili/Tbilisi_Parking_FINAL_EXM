package com.example.tbilisi_parking_final_exm.presentation.screen.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.domain.usecase.map.LatLngUseCase
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
    private val latLngUseCase: LatLngUseCase,
    private val markerLocationsUseCase: MarkerLocationsUseCase
) : ViewModel() {

    private val _mapState = MutableStateFlow(MapState())
    val mapState get() = _mapState.asStateFlow()

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.SetMarkers -> setMarkers(event.jsonString)
            is MapEvent.SetUserLocation -> setUserLocation(event.userLatLng)
        }
    }

    private fun setMarkers(jsonString: String) {
        viewModelScope.launch {
            markerLocationsUseCase(jsonString = jsonString).forEach { getMarkerLocation ->
                latLngUseCase(address = getMarkerLocation.address)?.let {
                    _mapState.update { currentState ->
                        currentState.copy(markerLocation = getMarkerLocation.toPresentation(it))
                    }
                }

            }
        }
    }

    private fun setUserLocation(userLatLng: LatLng) {
        _mapState.update { currentState ->
            currentState.copy(userLatLng = userLatLng)
        }
    }
}