package com.example.tbilisi_parking_final_exm.presentation.screen.sign_up

import androidx.fragment.app.viewModels
import com.example.tbilisi_parking_final_exm.databinding.FragmentSignUpBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val viewModel: SignUpViewModel by viewModels()

    override fun bind() {
        viewModel.setUp()
    }

    override fun bindViewActionListeners() {

    }

    override fun bindObserves() {

    }
}