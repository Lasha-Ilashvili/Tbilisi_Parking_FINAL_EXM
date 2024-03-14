package com.example.tbilisi_parking_final_exm.presentation.model.cards


data class Card(
    val title: String,
    val zonalCard: ZonalCard
) {
    data class ZonalCard(
        val period: String,
        val price: Int
    )
}
