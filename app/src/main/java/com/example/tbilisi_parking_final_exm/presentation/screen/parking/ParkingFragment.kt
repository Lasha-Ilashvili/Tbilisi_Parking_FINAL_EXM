package com.example.tbilisi_parking_final_exm.presentation.screen.parking

import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.databinding.FragmentParkingBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


class ParkingFragment : BaseFragment<FragmentParkingBinding>(FragmentParkingBinding::inflate) {
    override fun bind() {

    }

    override fun bindViewActionListeners() {
        binding.tvAddVehicle.setOnClickListener {
            findNavController().navigate(ParkingFragmentDirections.actionParkingFragmentToAddVehicleFragment())
        }
    }

    override fun bindObserves() {
    }
}