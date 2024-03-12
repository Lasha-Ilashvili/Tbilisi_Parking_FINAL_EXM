package com.example.tbilisi_parking_final_exm.presentation.screen.parking.add_vehicle

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentAddVehicleBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.parking.add_vehicle.AddVehicleEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.state.parking.add_vehicle.AddVehicleState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddVehicleFragment :
    BaseFragment<FragmentAddVehicleBinding>(FragmentAddVehicleBinding::inflate) {

    private val viewModel: AddVehicleViewModel by viewModels()

    override fun bind() {


    }

    override fun bindViewActionListeners() {
        with(binding) {


            btnAddVehicle.setOnClickListener {
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

                addVehicle()
            }

            addTextListeners(listOf(etNameOfVehicle, etPlateNumber))

            imgBackArrow.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addVehicleState.collect {
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

    private fun handleState(state: AddVehicleState) = with(state) {
        binding.btnAddVehicle.isEnabled = isButtonEnabled

        errorTextInputLayout?.let {
            it.error = getString(R.string.invalid_input)
            it.isErrorEnabled = isErrorEnabled
        }

        errorMessage?.let {
            binding.root.showToast(it)
            viewModel.onEvent(AddVehicleEvent.ResetErrorMessage)
        }

        with(binding) {
            if (isLoading) {
                addVehicleProgressBar.root.visibility = View.VISIBLE
                etNameOfVehicle.visibility = View.GONE
                etPlateNumber.visibility = View.GONE
                tvExample.visibility = View.GONE
                btnAddVehicle.visibility = View.GONE
            } else {
                addVehicleProgressBar.root.visibility = View.GONE
                etNameOfVehicle.visibility = View.VISIBLE
                etPlateNumber.visibility = View.VISIBLE
                tvExample.visibility = View.VISIBLE
                btnAddVehicle.visibility = View.VISIBLE
            }
        }


    }

    private fun addVehicle() {
        viewModel.onEvent(
            AddVehicleEvent.AddVehicle(
                name = binding.etNameOfVehicle,
                plateNumber = binding.etPlateNumber
            )
        )
    }

    private fun addTextListeners(fields: List<TextInputLayout>) {
        fields.forEach { textInputLayout ->
            textInputLayout.editText?.doAfterTextChanged {
                viewModel.onEvent(AddVehicleEvent.SetButtonState(fields))
            }
        }
    }

    private fun handleNavigationEvent(event: AddVehicleViewModel.AddVehicleUiEvent) {
        findNavController().popBackStack()
    }
}