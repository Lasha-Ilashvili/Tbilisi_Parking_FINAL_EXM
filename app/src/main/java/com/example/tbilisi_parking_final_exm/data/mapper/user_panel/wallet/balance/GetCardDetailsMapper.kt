package com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.balance.CardDetailsDto
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetCardDetails


fun GetCardDetails.toData() = CardDetailsDto(
    userId = userId,
    cardNumber = cardNumber,
    date = date,
    cvv = cvv
)