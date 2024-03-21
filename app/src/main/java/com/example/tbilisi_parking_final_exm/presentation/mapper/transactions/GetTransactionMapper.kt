package com.example.tbilisi_parking_final_exm.presentation.mapper.transactions

import com.example.tbilisi_parking_final_exm.domain.model.transactions.GetTransaction
import com.example.tbilisi_parking_final_exm.presentation.model.transactions.Transaction


fun GetTransaction.toPresentation() = Transaction(
    amount = amount,
    car = car?.toPresentation(),
    cardNumber = getMaskedCardNumber(cardNumber),
    license = getLicense(licensePrice),
    transactionStatus = transactionStatus,
    transactionType = transactionType,
    recDate = recDate,
    id = id
)

private fun GetTransaction.GetVehicle.toPresentation() = Transaction.Vehicle(
    id = id,
    name = name,
    plateNumber = plateNumber
)

private fun getMaskedCardNumber(cardNumber: String?): String? = cardNumber?.let {
    val maskedPart = it.substring(it.length - 8, it.length - 4)
        .replace(Regex("\\d"), "*")
    val lastFourDigits = it.substring(it.length - 4)

    "$maskedPart $lastFourDigits"
}

private fun getLicense(licensePrice: Int?): Transaction.License? {
    return licensePrice?.let {
        Transaction.License(licenseType = getLicenseType(licensePrice))
    }
}

private fun getLicenseType(licensePrice: Int?): Transaction.License.LicenseType {
    return if (licensePrice == 20)
        Transaction.License.LicenseType.ZONAL_LICENSE
    else
        Transaction.License.LicenseType.PARKING_LICENSE
}