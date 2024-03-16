package com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetCardDetails
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.balance.CardDetails


fun CardDetails.toDomain() = GetCardDetails(
    userId = userId,
    cardNumber = cardNumber,
    date = date,
    cvv = cvv
)