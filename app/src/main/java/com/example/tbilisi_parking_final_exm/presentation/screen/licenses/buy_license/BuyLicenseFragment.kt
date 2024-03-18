package com.example.tbilisi_parking_final_exm.presentation.screen.licenses.buy_license

import android.graphics.Color
import android.view.View.GONE
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
import com.example.tbilisi_parking_final_exm.presentation.event.licenses.buy_license.BuyLicenseEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.state.licenses.buy_license.BuyLicenseState
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
    }

    /* IMPLEMENTATION DETAILS */

    private fun setLayout() = with(binding.cardLayout) {
        tvLicenseTitle.visibility = GONE
        tvPeriod.text = args.period
        tvPrice.text = args.price.toString()

        ivLicenseBackground.background.setTint(Color.parseColor(args.backgroundColor))
        ivLicenseBackground.background.alpha = 99
        ivLicenseBackground.setColorFilter(Color.parseColor(args.backgroundColor))
    }

    private fun addTextListeners(fields: List<TextInputLayout>) {
        fields.forEach { textInputLayout ->
            textInputLayout.editText?.doAfterTextChanged {
                viewModel.onEvent(BuyLicenseEvent.SetButtonState(fields))
            }
        }
    }

    private fun handleState(buyLicenseState: BuyLicenseState) = with(buyLicenseState) {
        binding.btnBuyLicense.isEnabled = isButtonEnabled

        errorTextInputLayout?.let {
            it.error = getString(R.string.invalid_input)
            it.isErrorEnabled = isErrorEnabled
        }
    }
}