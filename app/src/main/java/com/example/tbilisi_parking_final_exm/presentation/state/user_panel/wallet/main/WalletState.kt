package com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.main

import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.main.Balance
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.main.RememberedCard


data class WalletState(
    val isLoading: Boolean = false,
    val data: List<RememberedCard>? = null,
    val balance: Balance? = null,
    val isButtonEnabled: Boolean = false,
    val errorMessage: String? = null
)