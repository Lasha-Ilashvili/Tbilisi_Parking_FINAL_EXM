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
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.main.WalletEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.main.WalletState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(FragmentWalletBinding::inflate) {

    private val viewModel: WalletViewModel by viewModels()

    override fun bind() {
        viewModel.onEvent(WalletEvent.GetBalance)
        viewModel.onEvent(WalletEvent.GetRememberedCards)
    }

    override fun bindViewActionListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            addTextListeners(etAmount)

            btnPayNow.setOnClickListener {
                it.hideKeyboard()

                findNavController().navigate(
                    WalletFragmentDirections.actionWalletFragmentToBalanceFragment(
                        amount = etAmount.editText?.text.toString().toFloat(),
                    )
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

    private fun handleState(walletState: WalletState) = with(walletState) {
        binding.progressBar.root.visibility = if (isLoading) VISIBLE else GONE

        binding.btnPayNow.isEnabled = isButtonEnabled

        errorMessage?.let {
            binding.root.showToast(errorMessage)
            viewModel.onEvent(WalletEvent.ResetErrorMessage)
        }

        balance?.let {
            binding.tvBalance.text = it.balance.toString()
        }

        data?.let {
            println(it)
        }
    }
}