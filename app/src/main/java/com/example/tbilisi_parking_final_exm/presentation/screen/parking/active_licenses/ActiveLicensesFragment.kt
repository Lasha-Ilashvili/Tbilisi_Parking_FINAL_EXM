package com.example.tbilisi_parking_final_exm.presentation.screen.parking.active_licenses

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.databinding.FragmentActiveLicensesBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.parking.active_licenses.ActiveLicensesEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.restartApp
import com.example.tbilisi_parking_final_exm.presentation.extension.showAlertForLogout
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.screen.parking.active_licenses.adapter.ActiveLicensesListAdapter
import com.example.tbilisi_parking_final_exm.presentation.state.parking.active_licenses.ActiveLicensesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ActiveLicensesFragment :
    BaseFragment<FragmentActiveLicensesBinding>(FragmentActiveLicensesBinding::inflate) {

    private val viewModel: ActiveLicensesViewModel by viewModels()
    private val args: ActiveLicensesFragmentArgs by navArgs()

    override fun bind() {
        viewModel.onEvent(ActiveLicensesEvent.GetActiveLicenses(args.carId))
        setLayout()
    }

    override fun bindViewActionListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.activeLicensesState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun setLayout() = with(binding) {
        tvName.text = args.name
        tvPlateNumber.text = args.plateNumber
    }

    private fun handleState(activeLicensesState: ActiveLicensesState) = with(activeLicensesState) {
        binding.progressBar.root.visibility = if (isLoading) VISIBLE else GONE

        if (sessionCompleted) {
            requireContext().showAlertForLogout { restartApp(requireActivity()) }
        }

        errorMessage?.let {
            binding.root.showToast(it)
            viewModel.onEvent(ActiveLicensesEvent.ResetErrorMessage)
        }

        data?.let {
            binding.rvActiveLicenses.adapter = ActiveLicensesListAdapter().apply {
                submitList(it)
            }
        }
    }
}