package com.example.tbilisi_parking_final_exm.presentation.screen.log_in

import androidx.fragment.app.viewModels
import com.example.tbilisi_parking_final_exm.databinding.FragmentLogInBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.log_in.LogInEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()
    override fun bind() {
        binding.btnLogIn.setOnClickListener{
            logInUser()
        }
    }

    override fun bindViewActionListeners() {
    }

    override fun bindObserves() {
    }

    private fun logInUser(){
        viewModel.onEvent(
            LogInEvent.LogIn(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        )
    }
}