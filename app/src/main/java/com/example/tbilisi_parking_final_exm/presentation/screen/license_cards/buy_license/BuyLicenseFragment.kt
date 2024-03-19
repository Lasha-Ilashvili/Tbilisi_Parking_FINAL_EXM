package com.example.tbilisi_parking_final_exm.presentation.screen.license_cards.buy_license

import android.graphics.Color
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentBuyLicenseBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.license_cards.buy_license.BuyLicenseEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.state.license_cards.buy_license.BuyLicenseState
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

            addTextListeners(listOf(etPlateNumber, etPersonalNumber))

            btnBuyLicense.setOnClickListener {
                viewModel.onEvent(
                    BuyLicenseEvent.BuyLicense(
                        plateNumber = etPlateNumber,
                        personalNumber = etPersonalNumber,
                        descriptionId = args.id
                    )
                )

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

    private fun setLayout() = with(binding.cardLayout) {
        ivLicenseBackground.apply {

            background.setTint(Color.parseColor(args.backgroundColor))
            background.alpha = 99
            setColorFilter(Color.parseColor(args.backgroundColor))
        }

        tvLicenseTitle.text = args.validity
        tvPeriod.text = args.validity
        tvLicenseType.text = args.type
        tvPrice.text = args.price.toString()
    }

    private fun addTextListeners(fields: List<TextInputLayout>) {
        fields.forEach { textInputLayout ->
            textInputLayout.editText?.doAfterTextChanged {
                viewModel.onEvent(BuyLicenseEvent.SetButtonState(fields))
            }
        }
    }

    private fun handleState(buyLicenseState: BuyLicenseState) = with(buyLicenseState) {
        binding.progressBar.root.visibility = if (isLoading) View.VISIBLE else View.GONE

        binding.btnBuyLicense.isEnabled = isButtonEnabled

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