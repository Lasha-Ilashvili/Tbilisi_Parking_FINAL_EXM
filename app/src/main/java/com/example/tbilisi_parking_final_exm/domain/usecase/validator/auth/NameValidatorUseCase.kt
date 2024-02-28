package com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth

import javax.inject.Inject


class NameValidatorUseCase @Inject constructor() {

    private val nameRegex = Regex("^[a-zA-Z\\s]+$")
    operator fun invoke(name: String) = name.matches(nameRegex)
}