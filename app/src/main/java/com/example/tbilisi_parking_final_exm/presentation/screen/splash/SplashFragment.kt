package com.example.tbilisi_parking_final_exm.presentation.screen.splash

import androidx.fragment.app.viewModels
import com.example.tbilisi_parking_final_exm.databinding.FragmentSplashBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()
    override fun bind() {

    }

    override fun bindViewActionListeners() {
    }

    override fun bindObserves() {

    }
}