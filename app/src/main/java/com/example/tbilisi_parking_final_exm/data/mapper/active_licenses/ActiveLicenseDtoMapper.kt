package com.example.tbilisi_parking_final_exm.data.mapper.active_licenses

import com.example.tbilisi_parking_final_exm.data.mapper.license_cards.all_licenses.toDomain
import com.example.tbilisi_parking_final_exm.data.model.parking.active_licenses.ActiveLicenseDto
import com.example.tbilisi_parking_final_exm.domain.model.active_licenses.GetActiveLicense


fun ActiveLicenseDto.toDomain() = GetActiveLicense(
    id = id,
    recDate = recDate,
    getLicense = licenseDto.toDomain()
)