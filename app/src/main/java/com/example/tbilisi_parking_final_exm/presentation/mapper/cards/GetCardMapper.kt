package com.example.tbilisi_parking_final_exm.presentation.mapper.cards

import com.example.tbilisi_parking_final_exm.domain.model.cards.GetCard
import com.example.tbilisi_parking_final_exm.presentation.model.cards.Card


fun GetCard.toPresentation() = Card(
    title = title,
    zonalCard = zonalCard?.toPresentation()
)

fun GetCard.GetZonalCard.toPresentation() = Card.ZonalCard(
    period = period,
    price = price,
    backgroundColor = backgroundColor
)