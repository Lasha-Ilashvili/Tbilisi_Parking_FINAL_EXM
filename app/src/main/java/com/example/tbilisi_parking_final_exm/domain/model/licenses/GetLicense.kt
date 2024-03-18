package com.example.tbilisi_parking_final_exm.domain.model.licenses


data class GetLicense(
    val id: Int,
    val price: Double,
    val validity: Int,
    val getLicenseType: GetLicenseType
) {
    data class GetLicenseType(
        val name: String
    )
}