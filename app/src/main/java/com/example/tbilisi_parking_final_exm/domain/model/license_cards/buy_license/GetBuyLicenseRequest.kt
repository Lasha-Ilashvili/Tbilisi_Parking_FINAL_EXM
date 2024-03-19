package com.example.tbilisi_parking_final_exm.domain.model.license_cards.buy_license


data class GetBuyLicenseRequest(
    val vehicleId: Int,
    val descriptionId: Int,
    val userId: Int
)