package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet.balance

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentBalanceBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.balance.BalanceEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.applyFormatting
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.balance.BalanceState
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BalanceFragment : BaseFragment<FragmentBalanceBinding>(FragmentBalanceBinding::inflate) {

    private val viewModel: BalanceViewModel by viewModels()

    override fun bindViewActionListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            cardDetailsLayout.apply {
                formatInput(etCardNumberInput, etCardDateInput)

                addTextListeners(listOf(etCardNumber, etCardDate, etCVV))

                btnProceedToPayment.setOnClickListener {
                    it.hideKeyboard()

                    proceedToPayment()
                }
            }
        }
    }


    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.balanceState.collect {
                    handleState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
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
                viewModel.onEvent(BalanceEvent.SetButtonState(fields))
            }
        }
    }

    private fun proceedToPayment() = with(binding.cardDetailsLayout) {
        viewModel.onEvent(
            BalanceEvent.ProceedToPayment(
                cardNumber = etCardNumber,
                date = etCardDate,
                cvv = etCVV,
                isRememberCardChecked = chkRememberCard.isChecked
            )
        )
    }

    private fun handleState(balanceState: BalanceState) = with(balanceState) {
        binding.cardDetailsLayout.btnProceedToPayment.isEnabled = isButtonEnabled

        errorTextInputLayout?.let {
            if (isErrorEnabled)
                it.editText?.error = getString(R.string.invalid_input)
            else
                it.editText?.error = null
        }
    }

    private fun handleNavigationEvents(event: BalanceViewModel.BalanceUiEvent) {
        findNavController().popBackStack()
    }
}