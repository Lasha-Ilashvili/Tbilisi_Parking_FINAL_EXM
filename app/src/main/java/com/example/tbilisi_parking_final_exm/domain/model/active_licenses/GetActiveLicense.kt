package com.example.tbilisi_parking_final_exm.domain.model.active_licenses

import com.example.tbilisi_parking_final_exm.domain.model.license_cards.all_license.GetLicense

data class GetActiveLicense(
    val id: Int,
    val status: String,
    val recDate: String,
    val getLicense: GetLicense
)
