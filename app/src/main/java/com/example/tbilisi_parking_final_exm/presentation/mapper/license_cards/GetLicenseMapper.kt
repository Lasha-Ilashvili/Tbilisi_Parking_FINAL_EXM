package com.example.tbilisi_parking_final_exm.presentation.mapper.license_cards

import com.example.tbilisi_parking_final_exm.R.string.day
import com.example.tbilisi_parking_final_exm.R.string.week
import com.example.tbilisi_parking_final_exm.domain.model.license_cards.all_license.GetLicense
import com.example.tbilisi_parking_final_exm.presentation.model.license_cards.all_licenses.License


fun GetLicense.toPresentation() = License(
    id = id,
    price = price,
    validity = validityToPresentation(validity).toString(),
    licenseType = getLicenseType(getLicenseType.name)
)

private fun validityToPresentation(validity: Int) = if (validity > 1) week else day

private fun getLicenseType(type: String): License.LicenseType {
    return if (type == "PAID_PARKING")
        License.LicenseType.ZONAL_LICENSE
    else
        License.LicenseType.PARKING_LICENSE
}