package com.example.tbilisi_parking_final_exm.presentation.screen.parking.start_parking.parking_is_started

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.databinding.FragmentParkingIsStartedBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking.parking_is_started.ParkingIsStartedEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.formatDate
import com.example.tbilisi_parking_final_exm.presentation.extension.restartApp
import com.example.tbilisi_parking_final_exm.presentation.extension.showAlertForLogout
import com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking.ParkingIsStartedState
import com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking.Zone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ParkingIsStartedFragment : BaseFragment<FragmentParkingIsStartedBinding>(FragmentParkingIsStartedBinding::inflate) {

    private val viewModel: ParkingIsStartedViewModel by viewModels()
    private val args: ParkingIsStartedFragmentArgs by navArgs()
    override fun bind() {
        viewModel.onEvent(ParkingIsStartedEvent.StartTimer(args.startDate))
        viewModel.onEvent(ParkingIsStartedEvent.GetUserBalance)

        binding.tvActiveParkingDate.text = formatDate(args.startDate)
        setZoneButton(args.zone)
    }

    override fun bindViewActionListeners() {
        binding.btnFinishParking.setOnClickListener {
            finishParking()
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.parkingIsStartedState.collect{
                    handleState(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.parkingIsStartedUiEvent.collect{
                    handleUiState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.timerState.collect{
                    binding.tvTimer.text = it
                }
            }
        }
    }

    private fun setZoneButton(zone: Zone) {
        println("zone"+ zone.color)
         binding.btnZone.apply {
             setTextColor(zone.color)
             setIconResource(zone.icon)
             setIconTintResource(zone.color)
             setStrokeColorResource(zone.color)
             setTextColor(zone.color)
         }
    }


    private fun handleState(state: ParkingIsStartedState) {

        if(state.sessionCompleted) {
            requireContext().showAlertForLogout { restartApp(requireActivity()) }
        }

        state.balance?.let {
            binding.tvBalance.text = it.balance.toString()
        }
    }
    private fun finishParking() {
        viewModel.onEvent(ParkingIsStartedEvent.FinishParking(stationExternalId = args.stationExternalId, carId = args.carId))
    }

    private fun handleUiState(event: ParkingIsStartedViewModel.ParkingIsStartedUiEvent){
        when (event){
            ParkingIsStartedViewModel.ParkingIsStartedUiEvent.NavigateToParkingFragment -> navigateToParkingFragment()

        }
    }
    private fun navigateToParkingFragment() {
        findNavController().navigate(ParkingIsStartedFragmentDirections.actionParkingIsStartedFragmentToParkingFragment())
    }
}