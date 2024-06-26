package com.example.tbilisi_parking_final_exm.presentation.screen.bottomNavFragment

import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentBottomNavBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomNavFragment :
    BaseFragment<FragmentBottomNavBinding>(FragmentBottomNavBinding::inflate) {


    override fun bind() {
        setBottomNavBar()
        readPushToken()
    }

    override fun bindViewActionListeners() {
    }

    override fun bindObserves() {}

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
                        R.id.walletFragment,
                        R.id.startParkingFragment,
                        R.id.addVehicleFragment,
                        R.id.balanceFragment,
                        R.id.buyLicenseFragment,
                        R.id.parkingIsStartedFragment,
                        R.id.activeLicensesFragment
                    )

                if (destination.id in destinationsToHideBottomNav) {
                    this.visibility = View.GONE
                } else {
                    this.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun readPushToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                println(task.exception.toString())

                return@OnCompleteListener
            }

            val token = task.result

            println(token)
        })
    }
}

