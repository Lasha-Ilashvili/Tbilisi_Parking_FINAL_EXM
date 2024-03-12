package com.example.tbilisi_parking_final_exm.presentation.screen.parking.start_parking

import android.content.res.ColorStateList
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentStartParkingBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.parking.start_parking.StartParkingEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.state.parking.start_parking.StartParkingState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartParkingFragment :
    BaseFragment<FragmentStartParkingBinding>(FragmentStartParkingBinding::inflate) {

    private val viewModel: StartParkingViewModel by viewModels()
    private val args: StartParkingFragmentArgs by navArgs()

    enum class Zone(val cost: Int, val color: Int, val icon: Int) {
        A(1, R.color.dark_blue, R.drawable.ic_letter_a),
        B(2, R.color.yellow, R.drawable.ic_letter_b),
        C(3, R.color.green, R.drawable.ic_letter_c)
    }

    override fun bindViewActionListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            costLayout.apply {
                addTextListeners(etLotNumber)


                btnStartParking.setOnClickListener {
                    it.hideKeyboard()

                    val dialogBuilder = AlertDialog.Builder(requireContext())
                    setUpDialog(dialogBuilder)
                    dialogBuilder.create().show()
                }
            }

            setZoneListeners()

            btnNext.setOnClickListener {
                it.hideKeyboard()

                viewModel.onEvent(StartParkingEvent.SetCostLayoutState())
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.startParkingState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    override fun bind() {
        binding.tvPlateNumber.text = args.plateNumber
    }

    private fun addTextListeners(field: TextInputLayout) {
        field.editText?.doAfterTextChanged {
            viewModel.onEvent(StartParkingEvent.SetButtonState(field))
        }
    }

    private fun setZoneListeners() = with(binding) {
        btnZoneA.setOnClickListener {
            viewModel.onEvent(StartParkingEvent.SetZoneState())
        }

        btnZoneB.setOnClickListener {
            viewModel.onEvent(StartParkingEvent.SetZoneState(Zone.B))
        }

        btnZoneC.setOnClickListener {
            viewModel.onEvent(StartParkingEvent.SetZoneState(Zone.C))
        }
    }

    private fun handleState(startParkingState: StartParkingState) = with(startParkingState) {
        binding.btnNext.isEnabled = isButtonEnabled

        binding.costLayout.root.visibility =
            if (isCostLayoutEnabled) View.VISIBLE else View.GONE

        if (!isButtonEnabled) {
            viewModel.onEvent(StartParkingEvent.SetCostLayoutState(false))
        }

        zone.apply {
            setStartIcon(icon, color)
            binding.costLayout.tvCost.text = cost.toString()
        }
    }

    private fun setStartIcon(drawableResId: Int, colorResId: Int) = with(binding) {
        etLotNumber.startIconDrawable = ContextCompat.getDrawable(requireContext(), drawableResId)
        val color = ContextCompat.getColor(requireContext(), colorResId)
        etLotNumber.setStartIconTintList(ColorStateList.valueOf(color))
    }

    private fun setUpDialog(dialogBuilder: AlertDialog.Builder) = with(dialogBuilder) {
        setTitle(getString(R.string.start_parking))
        setMessage(getString(R.string.start_parking_dialog))

        setPositiveButton(getString(R.string.start)) { _, _ ->

        }

        setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }
    }
}