package com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetAddBalanceRequest
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.balance.AddBalanceRequest


fun AddBalanceRequest.toDomain() = GetAddBalanceRequest(
    userId = userId,
    cardId = cardId,
    amount = amount
)