package com.example.tbilisi_parking_final_exm.domain.usecase.validator

import javax.inject.Inject

class EmailValidatorUseCase @Inject constructor() {

    private val emailRegex = Regex(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

    operator fun invoke(email: String) = email.matches(emailRegex)
}