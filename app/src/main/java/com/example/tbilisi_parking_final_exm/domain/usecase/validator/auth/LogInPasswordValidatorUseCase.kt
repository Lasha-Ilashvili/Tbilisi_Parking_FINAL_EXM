package com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth

import javax.inject.Inject

private const val EXPECTED_LENGTH = 8

class LogInPasswordValidatorUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean {
        return password.length >= EXPECTED_LENGTH
    }
}