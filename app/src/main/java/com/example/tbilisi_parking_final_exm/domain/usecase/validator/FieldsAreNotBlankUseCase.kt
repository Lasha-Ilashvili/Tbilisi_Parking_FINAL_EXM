package com.example.tbilisi_parking_final_exm.domain.usecase.validator

import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject

class FieldsAreNotBlankUseCase @Inject constructor() {

    operator fun invoke(fields: List<TextInputLayout>): Boolean = fields.all {
        it.editText?.text.toString().isNotBlank()
    }
}