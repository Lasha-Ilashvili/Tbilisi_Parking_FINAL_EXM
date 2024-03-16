package com.example.tbilisi_parking_final_exm.presentation.screen.bottomNavFragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentBottomNavBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.bottomNav.BottomNavEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BottomNavFragment :
    BaseFragment<FragmentBottomNavBinding>(FragmentBottomNavBinding::inflate) {

    private val viewModel: BottomNavViewModel by viewModels()

    override fun bind() {
        setBottomNavBar()
    }

    override fun bindViewActionListeners() {
        binding.navigateToHome.setOnClickListener {
            viewModel.onEvent(BottomNavEvent.ClearDataStore)

        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    private fun handleNavigationEvents(event: BottomNavViewModel.BottomNavUiEvent) = with(event) {
        when (event) {
            is BottomNavViewModel.BottomNavUiEvent.NavigateToHomeFragment -> {
                findNavController().navigate(BottomNavFragmentDirections.actionBottomNavFragmentToHomeFragment())
            }
        }
    }

    private fun setBottomNavBar() {
        with(binding.bottomNav) {
            val nestedNavHostFragment = childFragmentManager.findFragmentById(
                R.id.nested_nav_host_fragment
            ) as NavHostFragment

            setupWithNavController(
                nestedNavHostFragment.navController
            )

            nestedNavHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
                val destinationsToHideBottomNav =
                    setOf(
                        R.id.profileFragment,
                        R.id.settingsFragment,
                        R.id.walletFragment,
                        R.id.startParkingFragment,
                        R.id.addVehicleFragment,
                        R.id.balanceFragment
                    )

                if (destination.id in destinationsToHideBottomNav) {
                    this.visibility = View.GONE
                } else {
                    this.visibility = View.VISIBLE
                }
            }
        }
    }

}

