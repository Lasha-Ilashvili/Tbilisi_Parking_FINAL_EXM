package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet.main

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.databinding.FragmentWalletBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.WalletEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.extension.restartApp
import com.example.tbilisi_parking_final_exm.presentation.extension.showAlertForLogout
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.cards.UserCard
import com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet.main.adapter.UserCardsListAdapter
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.WalletState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(FragmentWalletBinding::inflate) {

    private val viewModel: WalletViewModel by viewModels()

    override fun bind() {
        viewModel.onEvent(WalletEvent.GetBalance)
        viewModel.onEvent(WalletEvent.GetUserCards)
    }

    override fun bindViewActionListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            addTextListeners(etAmount)

            btnPayNow.setOnClickListener {
                it.hideKeyboard()

                navigateToBalance(
                    amount = etAmount.editText?.text.toString().toFloat()
                )
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.walletState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun addTextListeners(field: TextInputLayout) {
        field.editText?.doAfterTextChanged {
            viewModel.onEvent(WalletEvent.SetButtonState(field))
        }
    }

    private fun handleState(walletState: WalletState) = with(binding) {
        progressBar.root.visibility = if (walletState.isLoading) VISIBLE else GONE

        btnPayNow.isEnabled = walletState.isButtonEnabled

        if(walletState.sessionCompleted){
            requireContext().showAlertForLogout { restartApp(requireActivity()) }
        }

        walletState.errorMessage?.let {
            root.showToast(walletState.errorMessage)
            viewModel.onEvent(WalletEvent.ResetErrorMessage)
        }

        walletState.balance?.let {
            tvBalance.text = it.balance.toString()
        }

        walletState.data?.let {
            rvUserCards.adapter = UserCardsListAdapter().apply {
                if (btnPayNow.isEnabled)
                    onClick = ::navigationFromRecycler
                onDeleteClick = ::deleteCard
                submitList(it)
            }
        }
    }

    private fun deleteCard(cardId: Int) {
        viewModel.onEvent(WalletEvent.DeleteCard(cardId = cardId))
    }

    private fun navigationFromRecycler(card: UserCard) = with(card) {
        navigateToBalance(
            amount = binding.etAmount.editText?.text.toString().toFloat(),
            cardId = id.toString(),
            cardNumber = cardNumber,
            date = date,
            cvv = cvv
        )
    }

    private fun navigateToBalance(
        amount: Float,
        cardId: String? = null,
        cardNumber: String = "",
        date: String = "",
        cvv: String = ""
    ) {
        findNavController().navigate(
            WalletFragmentDirections.actionWalletFragmentToBalanceFragment(
                amount = amount,
                cardId = cardId,
                cardNumber = cardNumber,
                date = date,
                cvv = cvv
            )
        )
    }
}