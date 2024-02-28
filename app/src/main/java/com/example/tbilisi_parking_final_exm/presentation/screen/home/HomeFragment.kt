package com.example.tbilisi_parking_final_exm.presentation.screen.home

import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.databinding.FragmentHomeBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun bind() {

    }

    override fun bindViewActionListeners() {
        with(binding) {
            btnRegistration.setOnClickListener{
                navigateToRegistrationFragment()
            }

            btnSignUp.setOnClickListener{
                navigateToSignUpFragment()
            }
        }
    }

    override fun bindObserves() {
    }


    private fun navigateToRegistrationFragment() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRegistrationFragment())
    }

    private fun navigateToSignUpFragment(){
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLogInFragment())
    }
}