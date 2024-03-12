package com.example.tbilisi_parking_final_exm.domain.usecase.validator.wallet

import javax.inject.Inject

private const val EXPECTED_LENGTH = 3

class CvvValidatorUseCase @Inject constructor() {
    operator fun invoke(cardNumber: String): Boolean {
        return cardNumber.length == EXPECTED_LENGTH
    }
}