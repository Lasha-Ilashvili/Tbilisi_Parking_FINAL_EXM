package com.example.tbilisi_parking_final_exm.data.service.licenses

import com.example.tbilisi_parking_final_exm.data.model.licenses.LicenseDto
import retrofit2.Response
import retrofit2.http.GET

interface LicensesService {
    @GET("license/description")
    suspend fun getLicenses(): Response<List<LicenseDto>>
}