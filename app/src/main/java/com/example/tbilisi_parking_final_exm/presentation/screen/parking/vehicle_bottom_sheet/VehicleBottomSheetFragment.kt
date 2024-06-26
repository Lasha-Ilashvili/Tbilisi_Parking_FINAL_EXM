package com.example.tbilisi_parking_final_exm.presentation.screen.parking.vehicle_bottom_sheet

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentVehicleBottomSheetBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseBottomSheet
import com.example.tbilisi_parking_final_exm.presentation.event.parking.vehicle_bottom_sheet.VehicleBottomSheetEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.showAlertDialog
import com.example.tbilisi_parking_final_exm.presentation.extension.showSnackBar
import com.example.tbilisi_parking_final_exm.presentation.screen.parking.vehicle_bottom_sheet.adapter.VehicleBottomSheetListAdapter
import com.example.tbilisi_parking_final_exm.presentation.state.parking.vehicle_bottom_sheet.VehicleBottomSheetItems
import com.example.tbilisi_parking_final_exm.presentation.state.parking.vehicle_bottom_sheet.VehicleBottomSheetState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class VehicleBottomSheetFragment :
    BaseBottomSheet<FragmentVehicleBottomSheetBinding>(FragmentVehicleBottomSheetBinding::inflate) {

    private val viewModel: VehicleBottomSheetViewModel by viewModels()
    private val args: VehicleBottomSheetFragmentArgs by navArgs()
    override fun bind() {
        setBottomSheetListAdapter()
        with(binding) {
            tvVehicleName.text = args.vehicleName
            tvVehiclePlateNumber.text = args.vehiclePlateNumber
        }
    }

    override fun bindViewActionListeners() {
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.vehicleBottomSheetUiState.collect{
                    handleUiState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.vehicleBottomSheetState.collect{
                    handleState(it)
                }
            }
        }
    }

    private fun setBottomSheetListAdapter() {
        binding.vehicleBottomSheetRecycler.adapter = VehicleBottomSheetListAdapter().apply {
            submitList(VehicleBottomSheetItems.bottomSheetList)
            recyclerItemClickListener(this)
        }
    }

    private fun recyclerItemClickListener(vehicleBottomSheetListAdapter: VehicleBottomSheetListAdapter) = with(args) {
        vehicleBottomSheetListAdapter.setOnItemClickListener {
            when (it) {
                VehicleBottomSheetItems.EDIT.id -> {
                    findNavController().navigate(
                        VehicleBottomSheetFragmentDirections.actionVehicleBottomSheetFragmentToEditVehicleFragment(
                            vehicleName, vehicleId, vehiclePlateNumber,
                        )
                    )
                }

                VehicleBottomSheetItems.DELETE.id -> {
                    setUpDialog()
                }

                VehicleBottomSheetItems.ACTIVE_LICENSES.id -> {
                    findNavController().navigate(
                        VehicleBottomSheetFragmentDirections.actionVehicleBottomSheetFragmentToActiveLicensesFragment(
                            name = vehicleName, carId = vehicleId, plateNumber = vehiclePlateNumber
                        )
                    )
                }
            }
        }
    }

    private fun handleState(state: VehicleBottomSheetState){

        state.errorMessage?.let {
            binding.root.showSnackBar(it)
            viewModel.onEvent(VehicleBottomSheetEvent.ResetErrorMessage)
        }
    }

    private fun  handleUiState(event: VehicleBottomSheetViewModel.VehicleBottomSheetUiEvent) {
        when (event) {
            is VehicleBottomSheetViewModel.VehicleBottomSheetUiEvent.NavigateToParkingFragment -> navigateToParking()
        }
    }
    private fun  navigateToParking() {
        findNavController().navigate(VehicleBottomSheetFragmentDirections.actionVehicleBottomSheetFragmentToParkingFragment())
    }

    private fun setUpDialog() {
        requireContext().showAlertDialog(
            title = getString(R.string.delete),
            message = getString(R.string.are_you_sure_want_to_delete),
            positiveButtonText = getString(R.string.yes),
            negativeButtonText = getString(R.string.no),
            positiveButtonClickAction = {
                deleteVehicle()
            }
        )
    }

    private fun deleteVehicle() {
        viewModel.onEvent(VehicleBottomSheetEvent.DeleteVehicle(args.vehicleId))
        println("delete vehicle")
    }
}