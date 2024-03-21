package com.example.tbilisi_parking_final_exm.presentation.model.transactions

import com.example.tbilisi_parking_final_exm.R


data class Transaction(
    val amount: Double,
    val car: Vehicle?,
    val cardNumber: String?,
    val license: License?,
    val transactionStatus: String,
    val transactionType: String,
    val recDate: String,
    val id: Int
) {
    data class Vehicle(
        val id: Int,
        val name: String,
        val plateNumber: String
    )

    data class License(
        val licenseType: LicenseType
    ) {
        enum class LicenseType(val type: Int) {
            ZONAL_LICENSE(R.string.zonal_license),
            PARKING_LICENSE(R.string.parking_license)
        }
    }
}