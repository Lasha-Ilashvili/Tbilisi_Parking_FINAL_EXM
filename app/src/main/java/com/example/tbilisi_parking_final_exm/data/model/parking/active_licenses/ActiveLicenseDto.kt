package com.example.tbilisi_parking_final_exm.data.model.parking.active_licenses

import com.example.tbilisi_parking_final_exm.data.model.license_cards.all_licenses.LicenseDto
import com.squareup.moshi.Json

data class ActiveLicenseDto(
    val id: Int,
    @Json(name = "recDate")
    val recDate: String,
    @Json(name = "licenseDescriptionResponse")
    val licenseDto: LicenseDto
)