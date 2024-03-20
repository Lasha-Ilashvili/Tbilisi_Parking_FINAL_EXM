package com.example.tbilisi_parking_final_exm.domain.model.transactions


data class GetTransaction(
    val amount: Double,
    val userId: Int,
    val car: GetVehicle?,
    val cardNumber: String?,
    val licensePrice: Int?,
    val transactionStatus: String,
    val transactionType: String,
    val recDate: String,
    val id: Int
){
    data class GetVehicle (
        val id: Int,
        val name: String,
        val plateNumber: String
    )
}
