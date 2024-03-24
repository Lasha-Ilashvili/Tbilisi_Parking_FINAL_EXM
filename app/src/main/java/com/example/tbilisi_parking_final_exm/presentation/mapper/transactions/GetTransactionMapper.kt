package com.example.tbilisi_parking_final_exm.presentation.mapper.transactions

import com.example.tbilisi_parking_final_exm.domain.model.transactions.GetTransaction
import com.example.tbilisi_parking_final_exm.presentation.model.transactions.Transaction


fun GetTransaction.GetTransactionItem.toPresentation() = Transaction.TransactionItem(
    amount = amount,
    car = car?.toPresentation(),
    cardNumber = getMaskedCardNumber(cardNumber),
    license = getLicense(amount.toInt()),
    parkingStation = parkingStation?.toDomain(),
    transactionStatus = transactionStatus,
    transactionType = getTransactionType(transactionType),
    recDate = recDate,
    id = id
)

private fun GetTransaction.GetTransactionItem.GetVehicle.toPresentation(): Transaction.TransactionItem.Vehicle {
    return Transaction.TransactionItem.Vehicle(
        name = name,
        plateNumber = plateNumber
    )
}

private fun getMaskedCardNumber(cardNumber: String?): String? = cardNumber?.let {
    val maskedPart = it.substring(it.length - 8, it.length - 4)
        .replace(Regex("\\d"), "*")
    val lastFourDigits = it.substring(it.length - 4)

    "$maskedPart $lastFourDigits"
}

private fun getLicense(licensePrice: Int): Transaction.TransactionItem.LicenseType {
    return if (licensePrice == 20)
        Transaction.TransactionItem.LicenseType.ZONAL_LICENSE
    else
        Transaction.TransactionItem.LicenseType.PARKING_LICENSE
}

private fun getTransactionType(transactionType: String): Transaction.TransactionItem.TransactionType {
    return Transaction.TransactionItem.TransactionType.entries.find {
        it.name == transactionType
    }!!
}

private fun GetTransaction.GetTransactionItem.GetParkingStation.toDomain() = Transaction.TransactionItem.ParkingStation(
    externalId = externalId,
    address = address
)