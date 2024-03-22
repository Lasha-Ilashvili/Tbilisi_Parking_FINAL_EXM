package com.example.tbilisi_parking_final_exm.presentation.screen.license_cards.buy_license

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.CardDetailsBinding
import com.example.tbilisi_parking_final_exm.databinding.FragmentBuyLicenseBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.license_cards.buy_license.BuyLicenseEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.applyFormatting
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.extension.restartApp
import com.example.tbilisi_parking_final_exm.presentation.extension.showAlertForLogout
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.state.license_cards.buy_license.BuyLicenseState
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BuyLicenseFragment :
    BaseFragment<FragmentBuyLicenseBinding>(FragmentBuyLicenseBinding::inflate) {

    private val args: BuyLicenseFragmentArgs by navArgs()
    private val viewModel: BuyLicenseViewModel by viewModels()

    override fun bind() {
        setLayout()
    }

    override fun bindViewActionListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            val fields = listOf(etPlateNumber, etPersonalNumber)

            addTextListeners(fields)

            rbPlasticCard.setOnCheckedChangeListener { _, isChecked ->
                handleCheckedState(isChecked, fields)
            }

            btnBuyLicense.setOnClickListener {
                buyLicense()

                it.hideKeyboard()
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.buyLicenseState.collect {
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

    private fun setLayout() = with(binding.licenseLayout) {
        ivLicenseBackground.background.setTint(
            ContextCompat.getColor(
                requireContext(),
                args.backgroundColor
            )
        )
        tvLicenseTitleStatic.visibility = GONE
        tvPeriod.text = args.validity
        tvLicenseType.text = args.type
        tvPrice.text = args.price.toString()
        ivLicense.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.license_icon_tint
            )
        )
    }

    private fun handleCheckedState(isChecked: Boolean, fields: List<TextInputLayout>) =
        with(binding) {
            if (isChecked)
                setCardLayout(cardLayout)
            else {
                cardLayout.root.visibility = GONE
                viewModel.onEvent(BuyLicenseEvent.SetButtonState(fields))
                addTextListeners(fields)
            }
        }

    private fun setCardLayout(cardLayout: CardDetailsBinding) = with(cardLayout) {
        setVisibilities(cardLayout)

        formatInput(etCardNumberInput, etCardDateInput)

        val fields = listOf(
            binding.etPlateNumber,
            binding.etPersonalNumber,
            cardLayout.etCardNumber,
            cardLayout.etCardDate,
            cardLayout.etCVV
        )

        viewModel.onEvent(BuyLicenseEvent.SetButtonState(fields))

        addTextListeners(fields)
    }

    private fun setVisibilities(cardLayout: CardDetailsBinding) = with(cardLayout) {
        root.visibility = VISIBLE
        tvCardDetails.visibility = GONE
        tvRememberCard.visibility = GONE
        chkRememberCard.visibility = GONE
        btnProceedToPayment.visibility = GONE
    }

    private fun formatInput(cardNumber: TextInputEditText, cardDate: TextInputEditText) {
        cardNumber.applyFormatting(symbol = " ", separator = 4)
        cardDate.applyFormatting(symbol = "/", separator = 2)
    }

    private fun addTextListeners(fields: List<TextInputLayout>) {
        fields.forEach { textInputLayout ->
            textInputLayout.editText?.doAfterTextChanged {
                viewModel.onEvent(BuyLicenseEvent.SetButtonState(fields))
            }
        }
    }

    private fun buyLicense() = with(binding) {
        viewModel.onEvent(
            if (rbPlasticCard.isChecked)
                BuyLicenseEvent.BuyLicense(
                    plateNumber = etPlateNumber,
                    personalNumber = etPersonalNumber,
                    descriptionId = args.id,
                    cardNumber = cardLayout.etCardNumber,
                    date = cardLayout.etCardDate,
                    cvv = cardLayout.etCVV
                )
            else
                BuyLicenseEvent.BuyLicense(
                    plateNumber = etPlateNumber,
                    personalNumber = etPersonalNumber,
                    descriptionId = args.id,
                )
        )
    }

    private fun handleState(buyLicenseState: BuyLicenseState) = with(buyLicenseState) {
        binding.progressBar.root.visibility = if (isLoading) VISIBLE else GONE

        binding.btnBuyLicense.isEnabled = isButtonEnabled

        if (sessionCompleted) {
            requireContext().showAlertForLogout { restartApp(requireActivity()) }
        }

        errorMessage?.let {
            binding.root.showToast(errorMessage)
            viewModel.onEvent(BuyLicenseEvent.ResetErrorMessage)
        }

        errorTextInputLayout?.let {
            it.error = getString(R.string.invalid_input)
            it.isErrorEnabled = isErrorEnabled
        }
    }

    private fun handleNavigationEvents(event: BuyLicenseViewModel.BuyLicenseUiEvent) {
        findNavController().popBackStack()
    }
}