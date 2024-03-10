package com.example.tbilisi_parking_final_exm.presentation.screen.log_in

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentLogInBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.log_in.LogInEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.state.log_in.LogInState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()
    override fun bind() {

    }

    override fun bindViewActionListeners() {
        with(binding) {
            btnLogIn.setOnClickListener {
                val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

                logInUser()

            }

            addTextListeners(listOf(etEmail, etPassword))

            imgBackArrow.setOnClickListener {
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logInState.collect {
                    handleState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                setFragmentResultListener("requestKey") { _, bundle ->
                    val email = bundle.getString("email")
                    val password = bundle.getString("password")

                    with(binding) {
                        etEmailInput.setText(email)
                        etPasswordInput.setText(password)
                    }

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleUiState(it)
                }
            }
        }
    }

    private fun handleUiState(event: LogInViewModel.LoginUiEvent) {
        findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToBottomNavFragment())
    }


    private fun logInUser() {
        viewModel.onEvent(
            LogInEvent.LogIn(
                email = binding.etEmail,
                password = binding.etPassword
            )
        )
    }

    private fun addTextListeners(fields: List<TextInputLayout>) {
        fields.forEach { textInputLayout ->
            textInputLayout.editText?.doAfterTextChanged {
                viewModel.onEvent(LogInEvent.SetButtonState(fields))
            }
        }
    }


    private fun handleState(state: LogInState) = with(state) {
        binding.btnLogIn.isEnabled = isButtonEnabled

        errorTextInputLayout?.let {
            it.error = getString(R.string.invalid_input)
            it.isErrorEnabled = isErrorEnabled
        }

        errorMessage?.let {
            binding.root.showToast(it)
            viewModel.onEvent(LogInEvent.ResetErrorMessage)
        }

        with(binding) {
            if (isLoading) {
                progressBar.root.visibility = View.VISIBLE
                etEmail.visibility = View.GONE
                etPassword.visibility = View.GONE
                btnLogIn.visibility = View.GONE

            } else {
                progressBar.root.visibility = View.GONE
                etEmail.visibility = View.VISIBLE
                etPassword.visibility = View.VISIBLE
                btnLogIn.visibility = View.VISIBLE
            }
        }
    }


}