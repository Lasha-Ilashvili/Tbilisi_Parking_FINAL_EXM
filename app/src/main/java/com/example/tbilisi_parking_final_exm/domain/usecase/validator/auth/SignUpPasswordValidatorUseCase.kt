package com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth

import javax.inject.Inject


private const val EXPECTED_LENGTH = 8

class SignUpPasswordValidatorUseCase @Inject constructor() {
    operator fun invoke(password: String, matchingPassword: String): Boolean =
        password.length >= EXPECTED_LENGTH && password == matchingPassword
}

