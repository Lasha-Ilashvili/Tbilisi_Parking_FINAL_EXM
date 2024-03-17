package com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.balance.AddBalanceRequestDto
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetAddBalanceRequest


fun GetAddBalanceRequest.toData() = AddBalanceRequestDto(
    userId = userId,
    cardId = cardId,
    amount = amount
)