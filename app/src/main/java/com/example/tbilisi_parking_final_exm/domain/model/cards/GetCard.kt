package com.example.tbilisi_parking_final_exm.domain.model.cards


data class GetCard(
    val title: String,
    val zonalCard: GetZonalCard? = null
) {
    data class GetZonalCard(
        val period: String,
        val price: Int,
        val backgroundColor: String
    )
}
