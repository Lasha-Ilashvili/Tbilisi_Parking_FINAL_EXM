package com.example.tbilisi_parking_final_exm.presentation.screen.parking.main

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
import com.example.tbilisi_parking_final_exm.presentation.extension.restartApp
import com.example.tbilisi_parking_final_exm.presentation.extension.showAlertForLogout
import com.example.tbilisi_parking_final_exm.presentation.extension.showSnackBar
import com.example.tbilisi_parking_final_exm.presentation.screen.parking.main.adapter.ParkingVehiclesListAdapter
import com.example.tbilisi_parking_final_exm.presentation.state.parking.ParkingState
import com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking.Zone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParkingFragment : BaseFragment<FragmentParkingBinding>(FragmentParkingBinding::inflate) {

    private val viewModel: ParkingViewModel by viewModels()
    private lateinit var parkingVehiclesListAdapter: ParkingVehiclesListAdapter


    override fun bind() {
        viewModel.onEvent(ParkingEvent.CheckActiveParking)
        viewModel.onEvent(ParkingEvent.GetUserBalance)
        viewModel.onEvent(ParkingEvent.FetchAllVehicle)
        setUpRecycler()


    }

    override fun bindViewActionListeners() {
        with(binding) {
            tvAddVehicle.setOnClickListener {
                findNavController().navigate(ParkingFragmentDirections.actionParkingFragmentToAddVehicleFragment())
            }

            setRefreshListener()
        }

        vehicleClickListener()
        vehicleDotsClickListener()
    }

    private fun setRefreshListener() = with(binding.vehicleSwipeRefresh) {
        setOnRefreshListener {
            isRefreshing = false

            viewModel.onEvent(ParkingEvent.CheckActiveParking)
            viewModel.onEvent(ParkingEvent.GetUserBalance)
            viewModel.onEvent(ParkingEvent.FetchAllVehicle)
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.parkingUiEvent.collect {
                    handleUiEvent(it)
                }
            }
        }
    }

    private fun handleUiEvent(event: ParkingViewModel.ParkingUiEvent) {
        when (event) {
            is ParkingViewModel.ParkingUiEvent.NavigateToTimer -> navigationEvent(
                event.stationExternalId,
                event.carId,
                event.startDate,
                event.zone

            )
        }
    }

    private fun navigationEvent(
        stationExternalId: String,
        carId: Int,
        startDate: String,
        zone: Zone
    ) {
        findNavController().navigate(
            ParkingFragmentDirections.actionParkingFragmentToParkingIsStartedFragment(
                stationExternalId = stationExternalId,
                carId = carId,
                startDate = startDate,
                zone = zone
            )
        )
    }

    private fun vehicleClickListener() {
        parkingVehiclesListAdapter.setOnItemClickListener { id, _, plateNumber ->
            findNavController().navigate(
                ParkingFragmentDirections.actionParkingFragmentToStartParkingFragment(
                    plateNumber = plateNumber, carId = id
                )
            )
        }
    }

    private fun vehicleDotsClickListener() {
        parkingVehiclesListAdapter.setOnItemDotsClickListener { id, name, plateNumber ->
            findNavController().navigate(
                ParkingFragmentDirections.actionParkingFragmentToVehicleBottomSheetFragment(
                    vehicleName = name,
                    vehiclePlateNumber = plateNumber,
                    vehicleId = id
                )
            )
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

        if (sessionCompleted) {
            requireContext().showAlertForLogout { restartApp(requireActivity()) }

        }


        vehicles?.let {
            parkingVehiclesListAdapter.submitList(it)
        }

        errorMessage?.let {
            binding.root.showSnackBar(it)
            viewModel.onEvent(ParkingEvent.ResetErrorMessage)
        }

        balance?.let {
            binding.tvBalance.text = it.balance.toString()
        }

        with(binding) {
            if (isLoading) {
                parkingProgressBar.root.visibility = View.VISIBLE
                tvAddVehicle.visibility = View.GONE
                recyclerVehicle.visibility = View.GONE
                tvBalanceText.visibility = View.GONE
                tvBalance.visibility = View.GONE
                tvCurrency.visibility = View.GONE

            } else {
                parkingProgressBar.root.visibility = View.GONE
                tvAddVehicle.visibility = View.VISIBLE
                recyclerVehicle.visibility = View.VISIBLE
                tvBalanceText.visibility = View.VISIBLE
                tvBalance.visibility = View.VISIBLE
                tvCurrency.visibility = View.VISIBLE
            }
        }
    }
}