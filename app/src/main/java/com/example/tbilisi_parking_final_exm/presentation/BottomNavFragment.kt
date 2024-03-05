package com.example.tbilisi_parking_final_exm.presentation

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentBottomNavBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment


class BottomNavFragment :
    BaseFragment<FragmentBottomNavBinding>(FragmentBottomNavBinding::inflate) {

    override fun bind() {
        setBottomNavBar()
    }

    override fun bindViewActionListeners() {}

    override fun bindObserves() {}

    private fun setBottomNavBar() {
        with(binding.bottomNav) {
            val nestedNavHostFragment = childFragmentManager.findFragmentById(
                R.id.nested_nav_host_fragment
            ) as NavHostFragment

            setupWithNavController(
                nestedNavHostFragment.navController
            )
        }
    }
}