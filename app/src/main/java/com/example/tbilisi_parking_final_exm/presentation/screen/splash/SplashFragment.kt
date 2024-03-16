package com.example.tbilisi_parking_final_exm.presentation.screen.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.databinding.FragmentSplashBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()
    override fun bind() {
    }

    override fun bindViewActionListeners() {

    }

    private fun handleUiState(event: SplashViewModel.SplashUiEvent){
        when(event) {
            is SplashViewModel.SplashUiEvent.NavigateToHomeFragment -> {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            }
            is SplashViewModel.SplashUiEvent.NavigateToBottomNavFragment -> {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToBottomNavFragment())
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiEvent.collect{
                    handleUiState(it)
                }
            }
        }
    }
}