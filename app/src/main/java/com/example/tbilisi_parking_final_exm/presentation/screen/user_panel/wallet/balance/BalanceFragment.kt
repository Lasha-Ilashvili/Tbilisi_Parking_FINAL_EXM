package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet.balance

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentBalanceBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.wallet.balance.BalanceEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.applyFormatting
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.extension.showSnackBar
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.wallet.balance.BalanceState
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BalanceFragment : BaseFragment<FragmentBalanceBinding>(FragmentBalanceBinding::inflate) {

    private val viewModel: BalanceViewModel by viewModels()
    private val args: BalanceFragmentArgs by navArgs()

    override fun bind() {
        setFields()

    }

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

    private fun setFields() {
        with(binding.cardDetailsLayout) {
            etCardNumberInput.setText(args.cardNumber.takeUnless { it.isNullOrEmpty() } ?: "")
            etCardDateInput.setText(args.date.takeUnless { it.isNullOrEmpty() } ?: "")
            etCVVInput.setText(args.cvv.takeUnless { it.isNullOrEmpty() } ?: "")

            applyFormatting(etCardNumberInput)

            if (!etCardNumberInput.text.isNullOrEmpty() &&
                !etCardDateInput.text.isNullOrEmpty() &&
                !etCVVInput.text.isNullOrEmpty()
            ) {
                viewModel.onEvent(BalanceEvent.SetButtonState(listOf(etCardNumber, etCardDate, etCVV)))
            }
        }
    }

    private fun formatInput(cardNumber: TextInputEditText, cardDate: TextInputEditText) {
        cardNumber.applyFormatting(symbol = " ", separator = 4)
        cardDate.applyFormatting(symbol = "/", separator = 2)
    }
    private fun applyFormatting(editText: EditText) {
        val text = editText.text.toString().replace(" ", "")
        val formattedText = StringBuilder()
        for (index in text.indices) {
            if (index % 4 == 0 && index > 0) {
                formattedText.append(" ")
            }
            formattedText.append(text[index])
        }
        editText.setText(formattedText)
        editText.setSelection(formattedText.length)
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
                cardId = args.cardId?.toIntOrNull(),
                isRememberCardChecked = chkRememberCard.isChecked,
                amount = args.amount.toDouble()
            )
        )
    }

    private fun handleState(balanceState: BalanceState) = with(balanceState) {

        binding.apply {
            if (isLoading) {
                cardDetailsLayout.root.visibility = GONE
                progressBar.root.visibility = VISIBLE
            } else {
                cardDetailsLayout.root.visibility = VISIBLE
                progressBar.root.visibility = GONE
            }
        }

        binding.cardDetailsLayout.btnProceedToPayment.isEnabled = isButtonEnabled

        errorMessage?.let {
            println(it)
            binding.root.showSnackBar(errorMessage)
            viewModel.onEvent(BalanceEvent.ResetErrorMessage)
        }

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