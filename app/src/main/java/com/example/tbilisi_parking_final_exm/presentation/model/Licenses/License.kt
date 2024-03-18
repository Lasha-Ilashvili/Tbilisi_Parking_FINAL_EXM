package com.example.tbilisi_parking_final_exm.presentation.model.Licenses


data class License(
    val title: String,
    val zonalCard: ZonalCard? = null
) {
    data class ZonalCard(
        val period: String,
        val price: Int,
        val backgroundColor: String
    )
}
