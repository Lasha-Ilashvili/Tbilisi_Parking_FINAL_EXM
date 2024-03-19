package com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth

import com.example.tbilisi_parking_final_exm.R
import javax.inject.Inject



class MatchingPasswordValidatorUseCase @Inject constructor() {

    operator fun invoke(password: String, matchingPassword: String): Pair<Boolean, Int> =
        Pair(password == matchingPassword, R.string.matching_password_error)
}