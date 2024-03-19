package com.example.tbilisi_parking_final_exm.presentation.screen.fines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FinesViewModel : ViewModel() {

    private lateinit var startDate: Date
    private var timerJob: Job? = null


    private val _finesState = MutableSharedFlow<String>()
    val finesState get() = _finesState


    fun startTimer(startDateString: String) {

        startDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .parse(startDateString) ?: Date()


        timerJob?.cancel() // Cancel existing job if any
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                updateTimer()
                delay(1000)
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
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

//        binding.tvTitle.text = formattedTime
        viewModelScope.launch {
            _finesState.emit(formattedTime)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}