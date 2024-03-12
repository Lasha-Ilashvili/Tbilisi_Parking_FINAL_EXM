package com.example.tbilisi_parking_final_exm.presentation.screen.parking

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbilisi_parking_final_exm.databinding.FragmentParkingBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.parking.ParkingEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.state.parking.ParkingState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParkingFragment : BaseFragment<FragmentParkingBinding>(FragmentParkingBinding::inflate) {

    private val viewModel: ParkingViewModel by viewModels()
    private lateinit var parkingVehiclesListAdapter: ParkingVehiclesListAdapter
    override fun bind() {
        viewModel.onEvent(ParkingEvent.FetchAllVehicle)
        setUpRecycler()
    }

    override fun bindViewActionListeners() {
        binding.tvAddVehicle.setOnClickListener {
            findNavController().navigate(ParkingFragmentDirections.actionParkingFragmentToAddVehicleFragment())
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.parkingState.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun setUpRecycler() {
        with(binding) {
            parkingVehiclesListAdapter = ParkingVehiclesListAdapter()
            recyclerVehicle.layoutManager = LinearLayoutManager(requireContext())
            recyclerVehicle.adapter = parkingVehiclesListAdapter
        }
    }

    private fun handleState(state: ParkingState) = with(state) {

        vehicles?.let {
            parkingVehiclesListAdapter.submitList(it)
        }

        errorMessage?.let {
            binding.root.showToast(it)
            viewModel.onEvent(ParkingEvent.ResetErrorMessage)
        }


        with(binding) {
            if (isLoading) {
                progressBar.root.visibility = View.VISIBLE
                tvAddVehicle.visibility = View.GONE
                recyclerVehicle.visibility = View.GONE

            } else {
                progressBar.root.visibility = View.GONE
                tvAddVehicle.visibility = View.VISIBLE
                recyclerVehicle.visibility = View.VISIBLE
            }
        }


    }
}