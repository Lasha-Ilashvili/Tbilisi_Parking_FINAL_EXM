package com.example.tbilisi_parking_final_exm.presentation.screen.sign_up.create_account

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentCreateAccountBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.sign_up.create_account.CreateAccountEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.state.sign_up.create_account.CreateAccountState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CreateAccountFragment :
    BaseFragment<FragmentCreateAccountBinding>(FragmentCreateAccountBinding::inflate) {

    private val args: CreateAccountFragmentArgs by navArgs()

    private val viewModel: CreateAccountViewModel by viewModels()

    override fun bindViewActionListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            addTextListeners(listOf(etEmail, etPassword, etRepeatPassword))

            btnNext.setOnClickListener {
                signUp()
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.createAccountState.collect {
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
                viewModel.onEvent(CreateAccountEvent.SetButtonState(fields))
            }
        }
    }

    private fun signUp() = with(binding) {
        viewModel.onEvent(
            CreateAccountEvent.SignUp(
                    firstName = args.firstName,
                    lastName = args.lastName,
                    email = etEmail,
                    mobileNumber = args.mobileNumber,
                    password = etPassword,
                    matchingPassword = etRepeatPassword,
                    personalNumber = args.personalNumber
            )
        )
    }

    private fun handleState(createAccountState: CreateAccountState) =
        with(createAccountState) {

            binding.progressBar.root.visibility =
                if (isLoading) View.VISIBLE else View.GONE

            errorMessage?.let {
                binding.root.showToast(errorMessage)
                viewModel.onEvent(CreateAccountEvent.ResetErrorMessage)
            }

            binding.btnNext.isEnabled = isButtonEnabled

            errorTextInputLayout?.let {
                it.error = getString(R.string.invalid_input)
                it.isErrorEnabled = isErrorEnabled
            }
        }

    private fun handleNavigationEvents(event: CreateAccountViewModel.CreateAccountUiEvent) =
        with(event) {
            when (this) {
                is CreateAccountViewModel.CreateAccountUiEvent.NavigateBackToLogIn -> {
                    val resultBundle = Bundle().apply {
                        putString("email", userCredentials.email)
                        putString("password", userCredentials.password)
                    }

                    parentFragmentManager.setFragmentResult("requestKey", resultBundle)

                    findNavController().navigate(
                        CreateAccountFragmentDirections.actionCreateAccountFragmentToLogInFragment()
                    )
                }
            }
        }
}