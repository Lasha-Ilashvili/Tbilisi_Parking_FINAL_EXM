package com.example.tbilisi_parking_final_exm.data.mapper.license_cards.buy_license

import com.example.tbilisi_parking_final_exm.data.model.license_cards.buy_license.BuyLicenseRequestDto
import com.example.tbilisi_parking_final_exm.domain.model.license_cards.buy_license.GetBuyLicenseRequest


fun GetBuyLicenseRequest.toData() = BuyLicenseRequestDto(
    carId = vehicleId,
    descriptionId = descriptionId,
    userId = userId
)