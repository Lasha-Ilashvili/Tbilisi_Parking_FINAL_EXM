package com.example.tbilisi_parking_final_exm.presentation.mapper.active_licenses

import com.example.tbilisi_parking_final_exm.domain.model.active_licenses.GetActiveLicense
import com.example.tbilisi_parking_final_exm.presentation.mapper.license_cards.toPresentation
import com.example.tbilisi_parking_final_exm.presentation.model.active_licenses.ActiveLicense


fun GetActiveLicense.toPresentation() = ActiveLicense(
    id = id,
    recDate = recDate,
    license = getLicense.toPresentation()
)