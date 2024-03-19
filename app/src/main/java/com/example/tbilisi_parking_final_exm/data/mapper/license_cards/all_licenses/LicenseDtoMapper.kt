package com.example.tbilisi_parking_final_exm.data.mapper.license_cards.all_licenses

import com.example.tbilisi_parking_final_exm.data.model.license_cards.all_licenses.LicenseDto
import com.example.tbilisi_parking_final_exm.domain.model.license_cards.all_license.GetLicense


fun LicenseDto.toDomain() = GetLicense(
    id = id,
    price = price,
    validity = validity,
    getLicenseType = licenseTypeDto.map { it.toDomain() }.first()
)

private fun LicenseDto.LicenseTypeDto.toDomain() =
    GetLicense.GetLicenseType(name = name)