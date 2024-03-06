package com.example.tbilisi_parking_final_exm.presentation.screen.map

import com.example.tbilisi_parking_final_exm.databinding.FragmentMapBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment


class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {
    override fun bind() {
    }

    override fun bindViewActionListeners() {
        binding.root.setOnClickListener {
        }
    }

    override fun bindObserves() {
    }
}