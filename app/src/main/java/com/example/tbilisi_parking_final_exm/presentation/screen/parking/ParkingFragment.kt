package com.example.tbilisi_parking_final_exm.presentation.screen.parking

import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.databinding.FragmentParkingBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment

class ParkingFragment : BaseFragment<FragmentParkingBinding>(FragmentParkingBinding::inflate) {
    override fun bind() {
        binding.root.setOnClickListener {
            findNavController().navigate(ParkingFragmentDirections.actionParkingFragmentToCardsFragment2())

        }
    }

    override fun bindViewActionListeners() {
    }

    override fun bindObserves() {
    }
}