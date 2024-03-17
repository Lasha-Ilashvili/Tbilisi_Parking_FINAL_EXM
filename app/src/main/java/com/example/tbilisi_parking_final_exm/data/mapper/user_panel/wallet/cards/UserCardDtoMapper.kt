package com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.cards.UserCardDto
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.cards.GetUserCard


fun UserCardDto.toDomain() = GetUserCard(
    id = id,
    cardNumber = cardNumber,
    date = date,
    cvv = cvv
)