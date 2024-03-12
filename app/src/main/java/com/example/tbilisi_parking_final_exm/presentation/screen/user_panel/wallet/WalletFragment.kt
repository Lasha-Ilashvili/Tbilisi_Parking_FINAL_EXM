package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentWalletBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.WalletEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.applyFormatting
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.WalletState
import com.google.android.material.textfield.TextInputEditText
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

            cardDetailsLayout.apply {
                formatInput(etCardNumberInput, etCardDateInput)

                addTextListeners(listOf(etAmount, etCardNumber, etCardDate, etCVV))


                btnProceedToPayment.setOnClickListener {
                    it.hideKeyboard()

                    proceedToPayment()
                }
            }

            btnPayNow.setOnClickListener {
                it.hideKeyboard()

                viewModel.onEvent(WalletEvent.SetCardLayoutState())
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

    private fun formatInput(cardNumber: TextInputEditText, cardDate: TextInputEditText) {
        cardNumber.applyFormatting(symbol = " ", separator = 4)
        cardDate.applyFormatting(symbol = "/", separator = 2)
    }

    private fun addTextListeners(fields: List<TextInputLayout>) {
        fields.forEach { textInputLayout ->
            textInputLayout.editText?.doAfterTextChanged {
                if (textInputLayout == binding.etAmount)
                    viewModel.onEvent(WalletEvent.SetPayNowButtonState(binding.etAmount))
                else
                    viewModel.onEvent(WalletEvent.SetProceedToPaymentButtonState(fields))
            }
        }
    }

    private fun proceedToPayment() = with(binding.cardDetailsLayout) {
        println(etCardNumber.editText?.text.toString())

        viewModel.onEvent(
            WalletEvent.ProceedToPayment(
                cardNumber = etCardNumber,
                date = etCardDate,
                cvv = etCVV
            )
        )
    }

    private fun handleState(walletState: WalletState) = with(walletState) {
        binding.btnPayNow.isEnabled = isPayNowButtonEnabled

        binding.cardDetailsLayout.btnProceedToPayment.isEnabled = isProceedToPaymentButtonEnabled

        binding.cardDetailsLayout.root.visibility = if (isCardLayoutEnabled) VISIBLE else GONE

        if (!isPayNowButtonEnabled) {
            viewModel.onEvent(WalletEvent.SetCardLayoutState(false))
        }

        errorTextInputLayout?.let {
            if (isErrorEnabled)
                it.editText?.error = getString(R.string.invalid_input)
            else
                it.editText?.error = null
        }
    }
}