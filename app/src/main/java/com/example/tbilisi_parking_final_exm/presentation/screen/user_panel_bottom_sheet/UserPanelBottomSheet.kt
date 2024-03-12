package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel_bottom_sheet

import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.databinding.FragmentUserPanelBottomSheetBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseBottomSheet
import com.example.tbilisi_parking_final_exm.presentation.screen.user_panel_bottom_sheet.adapter.BottomSheetListAdapter
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel_bottom_sheet.UserPanelBottomSheetState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserPanelBottomSheet :
    BaseBottomSheet<FragmentUserPanelBottomSheetBinding>(FragmentUserPanelBottomSheetBinding::inflate) {

    override fun bind() {
        setBottomSheetListAdapter()
    }

    override fun bindViewActionListeners() {

    }

    override fun bindObserves() {
    }

    private fun setBottomSheetListAdapter() {
        binding.userPanelBottomSheetRecycler.adapter = BottomSheetListAdapter().apply {
            submitList(UserPanelBottomSheetState.bottomSheetList)
            recyclerItemClickListener(this)
        }
    }

    private fun recyclerItemClickListener(bottomSheetListAdapter: BottomSheetListAdapter) {
        bottomSheetListAdapter.setOnItemClickListener {

            when (it) {
                UserPanelBottomSheetState.PROFILE.id -> {
                    findNavController().navigate(
                        UserPanelBottomSheetDirections.actionUserPanelBottomSheetToProfileFragment()
                    )
                }

                UserPanelBottomSheetState.WALLET.id -> {
                    findNavController().navigate(
                        UserPanelBottomSheetDirections.actionUserPanelBottomSheetToWalletFragment()
                    )
                }

                UserPanelBottomSheetState.SETTINGS.id -> {
                    findNavController().navigate(
                        UserPanelBottomSheetDirections.actionUserPanelBottomSheetToSettingsFragment()
                    )
                }
            }
        }
    }
}