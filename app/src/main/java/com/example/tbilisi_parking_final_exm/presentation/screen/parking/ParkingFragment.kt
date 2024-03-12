package com.example.tbilisi_parking_final_exm.presentation.screen.parking

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.databinding.FragmentParkingBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.parking.ParkingEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParkingFragment : BaseFragment<FragmentParkingBinding>(FragmentParkingBinding::inflate) {

    private val viewModel: ParkingViewModel by viewModels()
    override fun bind() {
        viewModel.onEvent(ParkingEvent.FetchAllVehicle)
    }

    override fun bindViewActionListeners() {
        binding.tvAddVehicle.setOnClickListener {
            findNavController().navigate(ParkingFragmentDirections.actionParkingFragmentToAddVehicleFragment())
        }
    }

    override fun bindObserves() {
    }
}