package com.example.tbilisi_parking_final_exm.presentation.screen.fines

import com.example.tbilisi_parking_final_exm.databinding.FragmentFinesBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.extension.restartApp


class FinesFragment : BaseFragment<FragmentFinesBinding>(FragmentFinesBinding::inflate) {

    override fun bind() {
        restartApp(requireActivity())
    }



    override fun bindViewActionListeners() {
    }

    override fun bindObserves() {
    }
}