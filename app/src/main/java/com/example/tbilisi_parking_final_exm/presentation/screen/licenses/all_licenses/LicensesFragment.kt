package com.example.tbilisi_parking_final_exm.presentation.screen.licenses.all_licenses

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.databinding.FragmentLicensesBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.licenses.all_licenses.LicensesEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.model.Licenses.License
import com.example.tbilisi_parking_final_exm.presentation.screen.licenses.all_licenses.adapter.LicensesRecyclerAdapter
import com.example.tbilisi_parking_final_exm.presentation.state.licenses.all_licenses.LicensesState
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
                viewModel.cardsState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    override fun bindViewActionListeners() {}

    private fun handleState(licensesState: LicensesState) = with(binding) {
        progressBar.root.visibility = if (licensesState.isLoading) VISIBLE else GONE

        licensesState.errorMessage?.let {
            root.showToast(it)
            viewModel.onEvent(LicensesEvent.ResetErrorMessage)
        }

        licensesState.data?.let {
            rvLicenses.adapter = LicensesRecyclerAdapter().apply {
                onClick = ::navigateToBuyCard
                submitList(it)
            }
        }
    }

    private fun navigateToBuyCard(zonalLicense: License.ZonalCard?) {
        zonalLicense?.let {
            findNavController().navigate(
                LicensesFragmentDirections.actionCardsFragmentToBuyLicenseFragment(
                    period = it.period,
                    price = it.price,
                    backgroundColor = it.backgroundColor
                )
            )
        }
    }
}