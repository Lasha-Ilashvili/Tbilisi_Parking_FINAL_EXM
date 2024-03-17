package com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.cards

import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetBalance
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.balance.Balance


fun GetBalance.toPresentation() = Balance(balance = balance)