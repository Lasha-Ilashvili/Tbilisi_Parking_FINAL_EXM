package com.example.tbilisi_parking_final_exm.data.model.transactions

import com.squareup.moshi.Json


data class TransactionDto(
    @Json(name = "content")
    val contentDto: List<TransactionItemDto>,
    val totalPages: Int
) {
    data class TransactionItemDto(
        val amount: Double,
        val car: VehicleDto,
        val card: CardDto,
        val licenseDescription: LicenseDescriptionDto,
        val transactionStatus: String,
        val transactionType: String,
        val recDate: String,
        val id: Int
    ) {
        data class VehicleDto(
            val id: Int?,
            val name: String?,
            val plateNumber: String?
        )

        data class CardDto(val cardNumber: String?)

        data class LicenseDescriptionDto(val price: Int?)
    }
}