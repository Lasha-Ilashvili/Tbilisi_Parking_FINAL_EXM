package com.example.tbilisi_parking_final_exm.presentation.screen.license_cards.all_licenses

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentLicensesBinding
import com.example.tbilisi_parking_final_exm.databinding.LicenseItemBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.license_cards.all_licenses.LicensesEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.restartApp
import com.example.tbilisi_parking_final_exm.presentation.extension.showAlertForLogout
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.model.license_cards.all_licenses.License
import com.example.tbilisi_parking_final_exm.presentation.state.license_cards.all_licenses.LicensesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LicensesFragment : BaseFragment<FragmentLicensesBinding>(FragmentLicensesBinding::inflate) {

    private val viewModel: LicensesViewModel by viewModels()

    override fun bind() {
        viewModel.onEvent(LicensesEvent.GetLicenses)
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.licensesState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    override fun bindViewActionListeners() {}

    private fun handleState(licensesState: LicensesState) = with(binding) {
        progressBar.root.visibility = if (licensesState.isLoading) VISIBLE else GONE

        if(licensesState.sessionCompleted) {
            requireContext().showAlertForLogout { restartApp(requireActivity()) }
        }

        licensesState.errorMessage?.let {
            root.showToast(it)
            viewModel.onEvent(LicensesEvent.ResetErrorMessage)
        }

        licensesState.data?.let {
            setLayout(it)
        }
    }

    private fun setLayout(licenses: List<License>) {
        val adaptedLicenses = adaptLicenses(licenses = licenses)

        adaptedLicenses.forEach {
            if (it.licenseType == License.LicenseType.ZONAL_LICENSE)
                setLicenses(it, binding.btnPaidParkingLicense)
            else
                setLicenses(it, binding.btnFreeParkingLicense)
        }
    }

    private fun setLicenses(license: License, btnLicense: LicenseItemBinding) {
        with(btnLicense) {
            ivLicenseBackground.apply {
                setOnClickListener {
                    navigateToBuyCard(license = license)
                }

                background.setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        license.licenseType.backgroundColor
                    )
                )
            }

            tvLicenseTitle.text = license.validity
            tvPeriod.text = license.validity
            tvLicenseType.text = getString(license.licenseType.type)
            tvPrice.text = license.price.toString()
            ivLicense.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.license_icon_tint
                )
            )
        }
    }

    private fun adaptLicenses(licenses: List<License>): List<License> {
        return licenses.map {
            it.copy(validity = getString(it.validity.toInt()))
        }
    }

    private fun navigateToBuyCard(license: License) = with(license) {
        findNavController().navigate(
            LicensesFragmentDirections.actionCardsFragmentToBuyLicenseFragment(
                id = id,
                type = getString(licenseType.type),
                validity = validity,
                backgroundColor = licenseType.backgroundColor,
                price = price
            )
        )
    }
}