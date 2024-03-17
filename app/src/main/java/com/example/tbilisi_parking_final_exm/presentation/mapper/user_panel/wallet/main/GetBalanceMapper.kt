package com.example.tbilisi_parking_final_exm.presentation.mapper.user_panel.wallet.main

import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.main.GetBalance
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.main.Balance


fun GetBalance.toPresentation() = Balance(balance = balance)