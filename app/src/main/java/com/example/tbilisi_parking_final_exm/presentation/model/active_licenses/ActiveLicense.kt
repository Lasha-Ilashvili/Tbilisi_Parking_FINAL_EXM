package com.example.tbilisi_parking_final_exm.presentation.model.active_licenses

import com.example.tbilisi_parking_final_exm.presentation.model.license_cards.all_licenses.License

data class ActiveLicense(
    val id: Int,
    val recDate: String,
    val license: License
)
