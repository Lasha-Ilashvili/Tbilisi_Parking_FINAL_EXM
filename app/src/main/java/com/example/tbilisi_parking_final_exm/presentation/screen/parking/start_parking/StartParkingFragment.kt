package com.example.tbilisi_parking_final_exm.presentation.screen.parking.start_parking

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentStartParkingBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking.StartParkingEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.extension.restartApp
import com.example.tbilisi_parking_final_exm.presentation.extension.showAlertDialog
import com.example.tbilisi_parking_final_exm.presentation.extension.showAlertForLogout
import com.example.tbilisi_parking_final_exm.presentation.extension.showSnackBar
import com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking.StartParkingState
import com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking.Zone
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartParkingFragment :
    BaseFragment<FragmentStartParkingBinding>(FragmentStartParkingBinding::inflate) {

    private val viewModel: StartParkingViewModel by viewModels()
    private val args: StartParkingFragmentArgs by navArgs()
    private var zoneValue: Zone = Zone.A

    override fun bind() {
        viewModel.onEvent(StartParkingEvent.GetBalance)
        binding.tvPlateNumber.text = args.plateNumber
    }

    override fun bindViewActionListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            costLayout.apply {
                addTextListeners(etLotNumber)


                btnStartParking.setOnClickListener {
                    it.hideKeyboard()

                    setUpDialog()
                }
            }

            setZoneListeners()

            setRefreshListener()

            btnNext.setOnClickListener {
                it.hideKeyboard()

                viewModel.onEvent(StartParkingEvent.SetCostLayoutState())
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.startParkingState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun addTextListeners(field: TextInputLayout) {
        field.editText?.doAfterTextChanged {
            viewModel.onEvent(StartParkingEvent.SetButtonState(field))
        }
    }

    private fun setZoneListeners() = with(binding) {
        btnZoneA.setOnClickListener {
            viewModel.onEvent(StartParkingEvent.SetZoneState())
        }

        btnZoneB.setOnClickListener {
            viewModel.onEvent(StartParkingEvent.SetZoneState(Zone.B))

        }

        btnZoneC.setOnClickListener {
            viewModel.onEvent(StartParkingEvent.SetZoneState(Zone.C))

        }
    }

    private fun setRefreshListener() = with(binding.startParkingSwipeRefresh) {
        setOnRefreshListener {
            isRefreshing = false

            viewModel.onEvent(StartParkingEvent.GetBalance)
        }
    }

    private fun handleState(startParkingState: StartParkingState) = with(startParkingState) {
        binding.progressBar.root.visibility = if (isLoading) View.VISIBLE else View.GONE

        binding.btnNext.isEnabled = isButtonEnabled

        if(sessionCompleted){
            requireContext().showAlertForLogout { restartApp(requireActivity()) }
        }

        errorMessage?.let {
            binding.root.showSnackBar(errorMessage)
            viewModel.onEvent(StartParkingEvent.ResetErrorMessage)
        }

        balance?.let {
            binding.tvBalance.text = it.balance.toString()
        }

        binding.costLayout.root.visibility =
            if (isCostLayoutEnabled) View.VISIBLE else View.GONE

        if (!isButtonEnabled) {
            viewModel.onEvent(StartParkingEvent.SetCostLayoutState(false))
        }

        zone.apply {
            setStartIcon(icon, color)
            binding.costLayout.tvCost.text = cost.toString()
            zoneValue = this
        }

        data?.let {
            findNavController().navigate(StartParkingFragmentDirections.actionStartParkingFragmentToParkingIsStartedFragment(
                stationExternalId = it.stationExternalId, carId = it.carId, startDate = it.startDate, zone = zoneValue,
            ))
        }
    }

    private fun setStartIcon(drawableResId: Int, colorResId: Int) = with(binding) {
        etLotNumber.startIconDrawable = ContextCompat.getDrawable(requireContext(), drawableResId)
        val color = ContextCompat.getColor(requireContext(), colorResId)
        etLotNumber.setStartIconTintList(ColorStateList.valueOf(color))
    }

    private fun setUpDialog() {
        requireContext().showAlertDialog(
            title = getString(R.string.start_parking),
            message = getString(R.string.start_parking_dialog),
            positiveButtonText = getString(R.string.start),
            negativeButtonText = getString(R.string.cancel),
            positiveButtonClickAction = {
                startParking()
            }
        )
    }
    private fun startParking() {
        val stationId = binding.etLotNumber.editText?.text.toString()
        val carId = args.carId

        viewModel.onEvent(StartParkingEvent.StartParking(
            stationExternalId = "$zoneValue$stationId",
            carId = carId
        ))
    }
}