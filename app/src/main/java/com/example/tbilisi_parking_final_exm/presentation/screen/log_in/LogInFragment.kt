package com.example.tbilisi_parking_final_exm.presentation.screen.log_in

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.databinding.FragmentLogInBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.log_in.LogInEvent
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
                logInUser()
            }

            addTextListeners(listOf(etEmail, etPassword))
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
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    private fun logInUser() {
        viewModel.onEvent(
            LogInEvent.LogIn(
                email = binding.etEmail.editText?.text.toString(),
                password = binding.etPassword.editText?.text.toString()
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

    private fun handleNavigationEvents(event: LogInViewModel.LogInUiEvent) {
        when (event) {
            is LogInViewModel.LogInUiEvent.NavigateToNestedNavGraph -> {
                findNavController().navigate(
                    LogInFragmentDirections.actionLogInFragmentToBottomNavFragment()
                )
            }
        }
    }
}