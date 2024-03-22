package com.example.tbilisi_parking_final_exm.presentation.model.transactions

import com.example.tbilisi_parking_final_exm.R


data class Transaction(
    val amount: Double,
    val car: Vehicle?,
    val cardNumber: String?,
    val license: LicenseType?,
    val transactionStatus: String,
    val transactionType: TransactionType,
    val recDate: String,
    val id: Int
) {
    data class Vehicle(
        val id: Int,
        val name: String,
        val plateNumber: String
    )

    enum class LicenseType(val type: Int) {
        ZONAL_LICENSE(R.string.zonal_license),
        PARKING_LICENSE(R.string.parking_license)
    }

    enum class TransactionType(val typeName: Int) {
        DEPOSIT_FROM_CARD(R.string.deposit_from_card),
        END_PARKING(R.string.end_parking),
        BUY_LICENSE_BY_CARD(R.string.buy_license_by_card),
        BUY_LICENSE_FROM_BALANCE(R.string.buy_license_from_balance)
    }
}