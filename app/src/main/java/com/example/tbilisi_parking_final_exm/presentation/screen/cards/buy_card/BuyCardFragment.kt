package com.example.tbilisi_parking_final_exm.presentation.screen.cards.buy_card

import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.databinding.FragmentBuyCardBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment


class BuyCardFragment : BaseFragment<FragmentBuyCardBinding>(FragmentBuyCardBinding::inflate) {

    private val safeArgs: BuyCardFragmentArgs by navArgs()

    override fun bind() {
        with(binding.cardLayout) {
            tvPeriod.text = safeArgs.period
            tvPrice.text = safeArgs.price.toString()
        }
    }

    override fun bindViewActionListeners() {

    }

    override fun bindObserves() {

    }
}