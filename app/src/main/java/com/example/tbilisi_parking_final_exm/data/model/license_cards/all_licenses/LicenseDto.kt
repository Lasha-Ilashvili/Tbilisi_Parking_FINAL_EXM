package com.example.tbilisi_parking_final_exm.data.model.license_cards.all_licenses

import com.squareup.moshi.Json


data class LicenseDto(
    val id: Int,
    val price: Int,
    val validity: Int,
    @Json(name = "parkingTypeResponses")
    val licenseTypeDto: List<LicenseTypeDto>
) {
    data class LicenseTypeDto(
        @Json(name = "name")
        val name: String
    )
}