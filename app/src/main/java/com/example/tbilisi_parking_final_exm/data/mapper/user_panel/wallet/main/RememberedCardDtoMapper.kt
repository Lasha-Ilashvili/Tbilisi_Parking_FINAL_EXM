package com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.main

import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.main.RememberedCardDto
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.main.GetRememberedCard


fun RememberedCardDto.toDomain() = GetRememberedCard(
    id = id,
    cardNumber = cardNumber,
    date = date,
    cvv = cvv
)