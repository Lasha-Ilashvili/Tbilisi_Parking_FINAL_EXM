package com.example.tbilisi_parking_final_exm.presentation.screen.sign_up.personal_information

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentPersonalInformationBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.sign_up.personal_information.PersonalInformationEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.hideKeyboard
import com.example.tbilisi_parking_final_exm.presentation.state.sign_up.personal_information.PersonalInformationState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PersonalInformationFragment :
    BaseFragment<FragmentPersonalInformationBinding>(FragmentPersonalInformationBinding::inflate) {

    private val viewModel: PersonalInformationViewModel by viewModels()

    override fun bindViewActionListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            addTextListeners(listOf(etPersonalNumber, etFirstName, etLastName, etMobileNumber))

            btnNext.setOnClickListener {
                it.hideKeyboard()

                proceedToCreateAccount()
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.personalInformationState.collect {
                    handleState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    override fun bind() {}

    private fun addTextListeners(fields: List<TextInputLayout>) {
        fields.forEach { textInputLayout ->
            textInputLayout.editText?.doAfterTextChanged {
                viewModel.onEvent(PersonalInformationEvent.SetButtonState(fields))
            }
        }
    }

    private fun proceedToCreateAccount() = with(binding) {
        viewModel.onEvent(
            PersonalInformationEvent.ProceedToCreateAccount(
                personalNumber = etPersonalNumber,
                firstName = etFirstName,
                lastName = etLastName,
                mobileNumber = etMobileNumber
            )
        )
    }

    private fun handleState(personalInformationState: PersonalInformationState) =
        with(personalInformationState) {

            binding.btnNext.isEnabled = isButtonEnabled

            errorTextInputLayout?.let {
                it.error = getString(R.string.invalid_input)
                it.isErrorEnabled = isErrorEnabled
            }
        }

    private fun handleNavigationEvents(event: PersonalInformationViewModel.PersonalInformationUiEvent) =
        with(event) {
            when (this) {
                is PersonalInformationViewModel.PersonalInformationUiEvent.NavigateToCreateAccount -> {
                    findNavController().navigate(
                        PersonalInformationFragmentDirections
                            .actionPersonalInformationFragmentToCreateAccountFragment(
                                personalNumber = personalNumber,
                                firstName = firstName,
                                lastName = lastName,
                                mobileNumber = mobileNumber
                            )
                    )
                }
            }
        }
}