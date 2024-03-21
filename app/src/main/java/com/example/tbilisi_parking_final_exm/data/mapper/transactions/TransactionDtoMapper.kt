package com.example.tbilisi_parking_final_exm.data.mapper.transactions

import com.example.tbilisi_parking_final_exm.data.model.transactions.TransactionDto
import com.example.tbilisi_parking_final_exm.domain.model.transactions.GetTransaction


fun TransactionDto.toDomain() = GetTransaction(
    amount = amount,
    car = car.toDomain(),
    cardNumber = card.cardNumber,
    licensePrice = licenseDescription.price,
    transactionStatus = transactionStatus,
    transactionType = transactionType,
    recDate = recDate,
    id = id
)

private fun TransactionDto.VehicleDto.toDomain(): GetTransaction.GetVehicle? {
    return id?.let {
        GetTransaction.GetVehicle(
            id = it,
            name = name!!,
            plateNumber = plateNumber!!
        )
    }
}