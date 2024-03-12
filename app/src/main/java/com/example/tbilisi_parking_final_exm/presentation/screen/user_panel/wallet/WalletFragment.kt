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
            }

//            btnAddCard.setOnClickListener {
//                it.hideKeyboard()
//                binding.cardDetailsLayout.root.visibility = VISIBLE
//                signUp()
//            }
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
                    viewModel.onEvent(WalletEvent.SetCardLayoutState(binding.etAmount))
                else
                    viewModel.onEvent(WalletEvent.SetButtonState(fields))
            }
        }
    }

    private fun proceedToPayment() = with(binding) {
//        viewModel.onEvent(
//            CreateAccountEvent.SignUp(
//                firstName = args.firstName,
//                lastName = args.lastName,
//                email = etEmail,
//                mobileNumber = args.mobileNumber,
//                password = etPassword,
//                matchingPassword = etRepeatPassword,
//                personalNumber = args.personalNumber
//            )
//        )
    }

    private fun handleState(walletState: WalletState) =
        with(walletState) {

            binding.cardDetailsLayout.root.visibility = if (isCardLayoutEnabled) VISIBLE else GONE

            binding.cardDetailsLayout.btnProceedToPayment.isEnabled = isButtonEnabled

            errorTextInputLayout?.let {
                it.error = getString(R.string.invalid_input)
                it.isErrorEnabled = isErrorEnabled
            }
        }
}