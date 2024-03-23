package com.example.tbilisi_parking_final_exm.data.mapper.transactions

import com.example.tbilisi_parking_final_exm.data.model.transactions.TransactionDto
import com.example.tbilisi_parking_final_exm.domain.model.transactions.GetTransaction


fun TransactionDto.toDomain() = GetTransaction(getContent = contentDto.map { dto ->
    dto.toDomain()
})

private fun TransactionDto.TransactionItemDto.toDomain() = GetTransaction.GetTransactionItem(
    amount = amount,
    car = car.toDomain(),
    cardNumber = card.cardNumber,
    licensePrice = licenseDescription.price,
    transactionStatus = transactionStatus,
    transactionType = transactionType,
    recDate = recDate,
    id = id
)

private fun TransactionDto.TransactionItemDto.VehicleDto.toDomain(): GetTransaction.GetTransactionItem.GetVehicle? {
    return id?.let {
        GetTransaction.GetTransactionItem.GetVehicle(
            id = it,
            name = name!!,
            plateNumber = plateNumber!!
        )
    }
}