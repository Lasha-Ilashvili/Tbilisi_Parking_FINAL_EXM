package com.example.tbilisi_parking_final_exm.data.model.license_cards.buy_license


data class BuyLicenseRequestDto(
    val carId: Int,
    val cardId:Int? = null,
    val descriptionId: Int,
    val userId: Int
)