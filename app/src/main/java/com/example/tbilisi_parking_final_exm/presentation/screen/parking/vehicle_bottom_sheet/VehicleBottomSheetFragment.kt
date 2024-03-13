package com.example.tbilisi_parking_final_exm.presentation.screen.parking.vehicle_bottom_sheet

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.databinding.FragmentVehicleBottomSheetBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseBottomSheet
import com.example.tbilisi_parking_final_exm.presentation.screen.parking.vehicle_bottom_sheet.adapter.VehicleBottomSheetListAdapter
import com.example.tbilisi_parking_final_exm.presentation.state.vehicle_bottom_sheet.VehicleBottomSheetState


class VehicleBottomSheetFragment :
    BaseBottomSheet<FragmentVehicleBottomSheetBinding>(FragmentVehicleBottomSheetBinding::inflate) {

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
    }

    private fun setBottomSheetListAdapter() {
        binding.vehicleBottomSheetRecycler.adapter = VehicleBottomSheetListAdapter().apply {
            submitList(VehicleBottomSheetState.bottomSheetList)
            recyclerItemClickListener(this)
        }
    }

    private fun recyclerItemClickListener(vehicleBottomSheetListAdapter: VehicleBottomSheetListAdapter) {
        vehicleBottomSheetListAdapter.setOnItemClickListener {
            when (it) {
                VehicleBottomSheetState.EDIT.id -> {
                    findNavController().navigate(
                        VehicleBottomSheetFragmentDirections.actionVehicleBottomSheetFragmentToEditVehicleFragment(
                            args.vehicleName, args.vehicleId, args.vehiclePlateNumber,
                        )
                    )
                }

                VehicleBottomSheetState.DELETE.id -> {
//                    remove vehicle
                }
            }
        }

    }
}