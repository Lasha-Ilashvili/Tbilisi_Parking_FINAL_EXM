package com.example.tbilisi_parking_final_exm.data.mapper.licenses

import com.example.tbilisi_parking_final_exm.data.model.licenses.LicenseDto
import com.example.tbilisi_parking_final_exm.domain.model.licenses.GetLicense


fun LicenseDto.toDomain() = GetLicense(
    id = id,
    price = price,
    validity = validity,
    getLicenseType = licenseTypeDto.map { it.toDomain() }.first()
)

private fun LicenseDto.LicenseTypeDto.toDomain() =
    GetLicense.GetLicenseType(name = name)