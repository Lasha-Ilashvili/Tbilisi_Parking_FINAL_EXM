package com.example.tbilisi_parking_final_exm.domain.usecase.validator

import javax.inject.Inject

private const val EXPECTED_LENGTH = 9

class MobileNumberValidatorUseCase @Inject constructor() {

    operator fun invoke(mobileNumber: String) =
        mobileNumber.length == EXPECTED_LENGTH

}