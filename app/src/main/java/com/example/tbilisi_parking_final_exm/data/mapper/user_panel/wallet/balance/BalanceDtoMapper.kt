package com.example.tbilisi_parking_final_exm.data.mapper.user_panel.wallet.balance

import com.example.tbilisi_parking_final_exm.data.model.user_panel.wallet.balance.BalanceDto
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.balance.GetBalance


fun BalanceDto.toDomain() = GetBalance(balance = balance)