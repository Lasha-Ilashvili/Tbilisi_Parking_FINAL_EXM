package com.example.tbilisi_parking_final_exm.domain.usecase.validator

import javax.inject.Inject

class PlateNumberValidatorUseCase @Inject constructor() {
    private val plateNumberRegex = Regex("^[A-Z]{2}\\d{3}[A-Z]{2}$")
    operator fun invoke(plateNumber: String) = plateNumber.matches(plateNumberRegex)
}