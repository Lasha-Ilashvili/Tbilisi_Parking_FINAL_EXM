package com.example.tbilisi_parking_final_exm.presentation.screen.parking.start_parking.parking_is_started

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.databinding.FragmentParkingIsStartedBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment


class ParkingIsStartedFragment : BaseFragment<FragmentParkingIsStartedBinding>(FragmentParkingIsStartedBinding::inflate) {

    private val viewModel: ParkingIsStartedViewModel by viewModels()
    private val args: ParkingIsStartedFragmentArgs by navArgs()
    override fun bind() {
        println("this is parkingIsStarted fragment"+args.stationExternalId +" "+ args.carId)
    }

    override fun bindViewActionListeners() {
    }

    override fun bindObserves() {
    }
}