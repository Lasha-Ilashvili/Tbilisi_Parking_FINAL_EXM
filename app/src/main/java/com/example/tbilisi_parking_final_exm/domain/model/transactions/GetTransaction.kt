package com.example.tbilisi_parking_final_exm.domain.model.transactions


data class GetTransaction(
    val getContent: List<GetTransactionItem>
) {
    data class GetTransactionItem(
        val amount: Double,
        val car: GetVehicle?,
        val cardNumber: String?,
        val parkingStation: GetParkingStation?,
        val transactionStatus: String,
        val transactionType: String,
        val recDate: String,
        val id: Int
    ) {
        data class GetVehicle(
            val name: String,
            val plateNumber: String
        )

        data class GetParkingStation(
            val externalId: String,
            val address: String
        )
    }
}