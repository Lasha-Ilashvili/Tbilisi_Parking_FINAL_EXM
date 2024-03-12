package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet

import android.view.View.VISIBLE
import com.example.tbilisi_parking_final_exm.databinding.FragmentWalletBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.extension.applyFormatting


class WalletFragment : BaseFragment<FragmentWalletBinding>(FragmentWalletBinding::inflate) {

    override fun bind() {

    }

    override fun bindViewActionListeners() {
        with(binding) {
            btnNext.setOnClickListener {
                binding.cardDetailsLayout.root.visibility = VISIBLE
            }

            cardDetailsLayout.etCardNumberInput.applyFormatting(symbol = " ", separator = 4)
            cardDetailsLayout.etCardDateInput.applyFormatting(symbol = "/", separator = 2)
        }
    }


    override fun bindObserves() {

    }
}