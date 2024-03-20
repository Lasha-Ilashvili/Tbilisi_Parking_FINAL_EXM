package com.example.tbilisi_parking_final_exm.presentation.screen.parking.start_parking.parking_is_started

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.parking.finish_parking.FinishParkingUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking.parking_is_started.ParkingIsStartedEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.parking.finish_parking.toDomain
import com.example.tbilisi_parking_final_exm.presentation.model.parking.finish_parking.FinishParking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ParkingIsStartedViewModel @Inject constructor(
    private val finishParkingUseCase: FinishParkingUseCase
) : ViewModel() {

    private lateinit var startDate: Date
    private var timerJob: Job? = null


    private val _parkingIsStartedUiEvent = MutableSharedFlow<ParkingIsStartedUiEvent>()
    val parkingIsStartedUiEvent get() = _parkingIsStartedUiEvent

    private val _timerState = MutableSharedFlow<String>()
    val timerState get() = _timerState

    fun onEvent(event: ParkingIsStartedEvent) {
        when (event) {

            is ParkingIsStartedEvent.StartTimer -> startTimer(event.parkingStartedAt)

            is ParkingIsStartedEvent.FinishParking -> finishParking(
                stationExternalId = event.stationExternalId,
                carId = event.carId
            )
        }
    }

    private fun startTimer(parkingStartedAt: String) {
        startDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .parse(parkingStartedAt) ?: Date()


        timerJob?.cancel() // Cancel existing job if any
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                updateTimer()
                delay(1000)
            }
        }
    }

    private fun updateTimer() {
        val currentTime = Date()
        val diffInMillis = currentTime.time - startDate.time

        val seconds = (diffInMillis / 1000).toInt()
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        val formattedTime = String.format(
            "%02d:%02d:%02d:%02d",
            days, hours % 24, minutes % 60, seconds % 60
        )

        viewModelScope.launch {
            _timerState.emit(formattedTime)
        }
    }




    private fun finishParking(stationExternalId: String, carId: Int) {
        viewModelScope.launch {
            finishParkingUseCase(
                finishParking = FinishParking(
                    stationExternalId = stationExternalId,
                    carId = carId
                ).toDomain()
            ).collect {
                when (it) {
                    is Resource.Success -> {
                        _parkingIsStartedUiEvent.emit(ParkingIsStartedUiEvent.NavigateToParkingFragment)
                    }

                    is Resource.Loading -> {}

                    is Resource.Error -> {}
                }
            }
        }
    }

    sealed interface ParkingIsStartedUiEvent{
        data object NavigateToParkingFragment:ParkingIsStartedUiEvent
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        println("job canceled")
    }

}