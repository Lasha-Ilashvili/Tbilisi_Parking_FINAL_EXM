package com.example.tbilisi_parking_final_exm.domain.usecase.validator.auth

import com.example.tbilisi_parking_final_exm.R
import javax.inject.Inject


private const val EXPECTED_LENGTH = 8
private const val UPPERCASE_REGEX = ".*[A-Z].*"
private const val DIGIT_REGEX = ".*[0-9].*"

class PasswordStrengthMeterUseCase @Inject constructor() {

    operator fun invoke(password: String): Pair<Int, Int> {
        if (password.length < EXPECTED_LENGTH) {
            return R.string.password_weak to R.color.red
        }

        val hasUppercase = password.matches(Regex(UPPERCASE_REGEX))
        val hasDigit = password.matches(Regex(DIGIT_REGEX))

        return if (hasUppercase && hasDigit) {
            R.string.password_strong to R.color.green
        } else {
            R.string.password_medium to R.color.yellow
        }
    }
}