package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.profile

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.databinding.FragmentProfileBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.profile.ProfileEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.state.profile.ProfileState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel : ProfileViewModel by viewModels()
    override fun bind() {
        viewModel.onEvent(ProfileEvent.FetchUserProfile)
    }

    override fun bindViewActionListeners() {
        binding.imgBackArrow.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileState.collect{
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: ProfileState) = with(state) {
        errorMessage?.let {
            binding.root.showToast(it)
            viewModel.onEvent(ProfileEvent.ResetErrorMessage)
        }

        profile?.let {
            with(binding) {
                tvFullName.text = it.fullName
                tvEmail.text = it.email
                tvPhoneNumber.text = it.phoneNumber
            }
        }

        with(binding){
            if(isLoading) {
                progressBar.root.visibility = View.VISIBLE
            } else {
                progressBar.root.visibility = View.GONE

            }
        }
    }



}