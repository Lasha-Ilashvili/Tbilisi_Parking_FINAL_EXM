package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.profile

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.databinding.FragmentProfileBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.user_panel.profile.ProfileEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.restartApp
import com.example.tbilisi_parking_final_exm.presentation.extension.showAlertForLogout
import com.example.tbilisi_parking_final_exm.presentation.extension.showSnackBar
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.profile.ProfileState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun bind() {
        viewModel.onEvent(ProfileEvent.FetchUserProfile)
    }

    override fun bindViewActionListeners() {
        with(binding) {
            btnBackArrow.setOnClickListener {
                findNavController().popBackStack()
            }

            setRefreshListener()
        }
    }

    private fun setRefreshListener() = with(binding.profileSwipeRefresh) {
        setOnRefreshListener {
            isRefreshing = false

            viewModel.onEvent(ProfileEvent.FetchUserProfile)
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileState.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: ProfileState) = with(state) {

        if (sessionCompleted) {
            requireContext().showAlertForLogout { restartApp(requireActivity()) }
        }

        errorMessage?.let {
            binding.root.showSnackBar(it)
            viewModel.onEvent(ProfileEvent.ResetErrorMessage)
        }

        profile?.let {
            with(binding) {
                tvFullName.text = it.fullName
                tvEmail.text = it.email
                tvPhoneNumber.text = it.phoneNumber
            }
        }

        with(binding) {
            if (isLoading) {
                progressBar.root.visibility = View.VISIBLE

            } else {
                progressBar.root.visibility = View.GONE

            }
        }
    }


}