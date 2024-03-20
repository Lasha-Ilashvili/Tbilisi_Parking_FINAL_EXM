package com.example.tbilisi_parking_final_exm.data.model.transactions


data class TransactionDto(
    val amount: Double,
    val userId: Int,
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