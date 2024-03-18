package com.example.tbilisi_parking_final_exm.presentation.screen.cards.main

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.databinding.FragmentCardsBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.cards.main.CardsEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.model.cards.Card
import com.example.tbilisi_parking_final_exm.presentation.screen.cards.main.adapter.CardsRecyclerAdapter
import com.example.tbilisi_parking_final_exm.presentation.state.cards.main.CardsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardsFragment : BaseFragment<FragmentCardsBinding>(FragmentCardsBinding::inflate) {

    private val viewModel: CardsViewModel by viewModels()

    override fun bind() {
        viewModel.onEvent(CardsEvent.GetCards)
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cardsState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    override fun bindViewActionListeners() {}

    private fun handleState(cardsState: CardsState) = with(binding) {
        progressBar.root.visibility = if (cardsState.isLoading) VISIBLE else GONE

        cardsState.errorMessage?.let {
            root.showToast(it)
            viewModel.onEvent(CardsEvent.ResetErrorMessage)
        }

        cardsState.data?.let {
            rvCards.adapter = CardsRecyclerAdapter().apply {
                onClick = ::navigateToBuyCard
                submitList(it)
            }
        }
    }

    private fun navigateToBuyCard(zonalCard: Card.ZonalCard?) {
        zonalCard?.let {
            findNavController().navigate(
                CardsFragmentDirections.actionCardsFragmentToBuyCardFragment(
                    period = it.period,
                    price = it.price,
                    backgroundColor = it.backgroundColor
                )
            )
        }
    }
}