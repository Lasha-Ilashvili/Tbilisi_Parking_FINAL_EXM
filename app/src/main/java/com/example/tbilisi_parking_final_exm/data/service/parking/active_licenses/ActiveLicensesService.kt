package com.example.tbilisi_parking_final_exm.data.service.parking.active_licenses

import com.example.tbilisi_parking_final_exm.data.model.parking.active_licenses.ActiveLicenseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ActiveLicensesService {
    @GET("license/car/{carId}")
    suspend fun getActiveLicenses(@Path("carId") carId: Int): Response<List<ActiveLicenseDto>>
}