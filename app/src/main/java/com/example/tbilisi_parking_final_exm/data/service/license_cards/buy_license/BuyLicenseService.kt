package com.example.tbilisi_parking_final_exm.data.service.license_cards.buy_license

import com.example.tbilisi_parking_final_exm.data.model.license_cards.buy_license.BuyLicenseRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BuyLicenseService {
    @POST("license")
    suspend fun buyLicense(@Body buyLicenseRequestDto: BuyLicenseRequestDto): Response<Unit>
}