package com.example.tbilisi_parking_final_exm.domain.usecase.validator.wallet

import javax.inject.Inject

private const val EXPECTED_LENGTH = 4

class DateValidatorUseCase @Inject constructor() {
    operator fun invoke(date: String): Boolean {
        return date.length == EXPECTED_LENGTH && validDate(date)

    }

    private fun validDate(date: String): Boolean {
        val day = date.substring(0, 2)
        val month = date.substring(2, 4)

        return day.toIntOrNull() in 1..31 && month.toIntOrNull() in 1..12
    }
}