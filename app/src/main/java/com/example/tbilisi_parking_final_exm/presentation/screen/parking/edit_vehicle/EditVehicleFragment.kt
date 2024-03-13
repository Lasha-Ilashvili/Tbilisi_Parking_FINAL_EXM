package com.example.tbilisi_parking_final_exm.presentation.screen.parking.edit_vehicle

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.databinding.FragmentEditVehicleBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.edit_vehicle.EditVehicleEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.state.edit_vehicle.EditVehicleState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditVehicleFragment :
    BaseFragment<FragmentEditVehicleBinding>(FragmentEditVehicleBinding::inflate) {

    private val viewModel: EditVehicleViewModel by viewModels()
    private val args: EditVehicleFragmentArgs by navArgs()

    override fun bind() {
        with(binding) {
            tvPlateNumber.text = args.vehiclePlateNumber
            etVehicleNameInput.setText(args.vehicleName)

            addTextListener(listOf(etVehicleName))

        }

    }

    override fun bindViewActionListeners() {
        binding.btnSave.setOnClickListener {
            it.hideKeyboard()

            editVehicle()
        }

        binding.btnBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.editVehicleState.collect {
                    handleState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvent(it)
                }
            }
        }
    }

    private fun handleState(state: EditVehicleState) = with(state) {
        binding.btnSave.isEnabled = isButtonEnabled
    }

    private fun editVehicle() {
        viewModel.onEvent(
            EditVehicleEvent.EditVehicle(
                vehicleId = args.vehicleId,
                name = binding.etVehicleName.editText?.text.toString()
            )
        )
    }

    private fun addTextListener(fields: List<TextInputLayout>) {
        fields.forEach { textInputLayout ->
            textInputLayout.editText?.doAfterTextChanged {
                viewModel.onEvent(EditVehicleEvent.SetButtonState(fields))
            }
        }
    }

    private fun handleNavigationEvent(event: EditVehicleViewModel.EditVehicleUiEvent) {
        findNavController().navigate(EditVehicleFragmentDirections.actionEditVehicleFragmentToParkingFragment())

    }
}