package com.example.tbilisi_parking_final_exm.domain.usecase.validator

class PasswordValidatorUseCase {

    operator fun invoke(password:String): Boolean {
        return password.length > 6
    }
}