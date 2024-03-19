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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ParkingIsStartedFragment : BaseFragment<FragmentParkingIsStartedBinding>(FragmentParkingIsStartedBinding::inflate) {

    private val viewModel: ParkingIsStartedViewModel by viewModels()
    private val args: ParkingIsStartedFragmentArgs by navArgs()
    override fun bind() {
        startParking()
    }

    override fun bindViewActionListeners() {
        binding.btnFinishParking.setOnClickListener {
            finishParking()
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.parkingIsStartedUiEvent.collect{
                    handleUiState(it)
                }
            }
        }
    }


    private fun startParking() {
        viewModel.onEvent(ParkingIsStartedEvent.StartParking(stationExternalId = args.stationExternalId, carId = args.carId))
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