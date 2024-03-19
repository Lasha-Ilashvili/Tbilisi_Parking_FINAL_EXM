package com.example.tbilisi_parking_final_exm.presentation.screen.parking.start_parking.parking_is_started

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.databinding.FragmentParkingIsStartedBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking.parking_is_started.ParkingIsStartedEvent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ParkingIsStartedFragment : BaseFragment<FragmentParkingIsStartedBinding>(FragmentParkingIsStartedBinding::inflate) {

    private val viewModel: ParkingIsStartedViewModel by viewModels()
    private val args: ParkingIsStartedFragmentArgs by navArgs()
    override fun bind() {
        startParking()
    }

    override fun bindViewActionListeners() {
    }

    override fun bindObserves() {
    }


    private fun startParking() {
        viewModel.onEvent(ParkingIsStartedEvent.StartParking(stationExternalId = args.stationExternalId, carId = args.carId))
    }
}