package com.example.tbilisi_parking_final_exm.data.mapper.cards

import com.example.tbilisi_parking_final_exm.data.model.cards.CardDto
import com.example.tbilisi_parking_final_exm.domain.model.cards.GetCard


fun CardDto.toDomain() = GetCard(
    title = title,
    zonalCard = zonalCard?.toDomain()
)

fun CardDto.ZonalCardDto.toDomain() = GetCard.GetZonalCard(
    period = period,
    price = price,
    backgroundColor = backgroundColor
)