package com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.cards.GetUserCard
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.cards.UserCard


fun GetUserCard.toPresentation() = UserCard(
    id = id,
    cardNumber = cardNumber,
    date = date,
    cvv = cvv
)