package com.example.tbilisi_parking_final_exm.data.model.licenses

import com.squareup.moshi.Json


data class LicenseDto(
    val id: Int,
    val price: Double,
    val validity: Int,
    @Json(name = "parkingTypeResponses")
    val licenseTypeDto: List<LicenseTypeDto>
) {
    data class LicenseTypeDto(
        @Json(name = "name")
        val name: String
    )
}