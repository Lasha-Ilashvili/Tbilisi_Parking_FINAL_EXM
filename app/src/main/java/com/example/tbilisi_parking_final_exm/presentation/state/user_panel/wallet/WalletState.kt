package com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet

import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.balance.Balance
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.cards.UserCard


data class WalletState(
    val isLoading: Boolean = false,
    val data: List<UserCard>? = null,
    val balance: Balance? = null,
    val isButtonEnabled: Boolean = false,
    val errorMessage: String? = null,
    val sessionCompleted: Boolean = false

)