package com.example.tbilisi_parking_final_exm.presentation.screen.cards.buy_card

import android.graphics.Color
import android.view.View.GONE
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentBuyCardBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.cards.buy_card.BuyCardEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.state.cards.buy_card.BuyCardState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BuyCardFragment : BaseFragment<FragmentBuyCardBinding>(FragmentBuyCardBinding::inflate) {

    private val args: BuyCardFragmentArgs by navArgs()
    private val viewModel: BuyCardViewModel by viewModels()

    override fun bind() {
        setLayout()
    }

    override fun bindViewActionListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            addTextListeners(listOf(etPlateNumber, etPersonalNumber))

            btnBuyCard.setOnClickListener {
                it.hideKeyboard()
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.buyCardState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun setLayout() = with(binding.cardLayout) {
        tvCardTitle.visibility = GONE
        tvPeriod.text = args.period
        tvPrice.text = args.price.toString()

        ivCardBackground.background.setTint(Color.parseColor(args.backgroundColor))
        ivCardBackground.background.alpha = 99
        ivCardBackground.setColorFilter(Color.parseColor(args.backgroundColor))
    }

    private fun addTextListeners(fields: List<TextInputLayout>) {
        fields.forEach { textInputLayout ->
            textInputLayout.editText?.doAfterTextChanged {
                viewModel.onEvent(BuyCardEvent.SetButtonState(fields))
            }
        }
    }

    private fun handleState(buyCardState: BuyCardState) = with(buyCardState) {
        binding.btnBuyCard.isEnabled = isButtonEnabled

        errorTextInputLayout?.let {
            it.error = getString(R.string.invalid_input)
            it.isErrorEnabled = isErrorEnabled
        }
    }
}