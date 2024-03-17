package com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.main

import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.main.GetRememberedCard
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.main.RememberedCard


fun GetRememberedCard.toPresentation() = RememberedCard(
    id = id,
    cardNumber = cardNumber,
    date = date,
    cvv = cvv
)