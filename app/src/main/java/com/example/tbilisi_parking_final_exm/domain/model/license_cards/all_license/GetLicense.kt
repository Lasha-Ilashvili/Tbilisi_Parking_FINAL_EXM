package com.example.tbilisi_parking_final_exm.domain.model.license_cards.all_license


data class GetLicense(
    val id: Int,
    val price: Int,
    val validity: Int,
    val getLicenseType: GetLicenseType
) {
    data class GetLicenseType(
        val name: String
    )
}