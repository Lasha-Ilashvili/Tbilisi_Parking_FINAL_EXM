package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet.main

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
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.main.WalletState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(FragmentWalletBinding::inflate) {

    private val viewModel: WalletViewModel by viewModels()

    override fun bindViewActionListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            addTextListeners(etAmount)

            btnPayNow.setOnClickListener {
                it.hideKeyboard()

                findNavController().navigate(WalletFragmentDirections.actionWalletFragmentToBalanceFragment())
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

    override fun bind() {}

    private fun addTextListeners(field: TextInputLayout) {
        field.editText?.doAfterTextChanged {
            viewModel.onEvent(WalletEvent.SetButtonState(field))
        }
    }

    private fun handleState(walletState: WalletState) = with(walletState) {
        binding.btnPayNow.isEnabled = isButtonEnabled
    }
}