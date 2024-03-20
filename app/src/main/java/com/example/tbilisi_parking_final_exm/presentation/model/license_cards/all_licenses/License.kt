package com.example.tbilisi_parking_final_exm.presentation.model.license_cards.all_licenses

import com.example.tbilisi_parking_final_exm.R


data class License(
    val id: Int,
    val price: Int,
    val validity: String,
    val licenseType: LicenseType
) {
    enum class LicenseType(val type: Int, val backgroundColor: String) {
        ZONAL_LICENSE(R.string.zonal_license, "#302081"),
        PARKING_LICENSE(R.string.parking_license, "#CD302081")
    }
}
