package com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth

import com.example.tbilisi_parking_final_exm.R
import javax.inject.Inject


private const val EXPECTED_LENGTH = 8

class PasswordValidatorUseCase @Inject constructor() {
    operator fun invoke(password: String): Pair<Boolean, Int> =
        (password.length >= EXPECTED_LENGTH) to R.string.password_error
}