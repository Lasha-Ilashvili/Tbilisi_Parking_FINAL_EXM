package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel_bottom_sheet

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbilisi_parking_final_exm.databinding.FragmentUserPanelBottomSheetBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseBottomSheet
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel_bottom_sheet.UserPanelBottomSheetState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserPanelBottomSheet : BaseBottomSheet<FragmentUserPanelBottomSheetBinding>(FragmentUserPanelBottomSheetBinding::inflate){

    private lateinit var bottomSheetListAdapter: BottomSheetListAdapter

    override fun bind() {
        setBottomSheetListAdapter()
    }

    override fun bindViewActionListeners() {

    }

    override fun bindObserves() {
    }

    private fun setBottomSheetListAdapter() {
        bottomSheetListAdapter = BottomSheetListAdapter()

        with(binding.userPanelBottomSheetRecycler) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bottomSheetListAdapter
        }

        bottomSheetListAdapter.submitList(UserPanelBottomSheetState.bottomSheetList)

        recyclerItemClickListener()
    }

    private fun recyclerItemClickListener() {
        bottomSheetListAdapter.setOnItemClickListener {
            when(it) {
                0 -> findNavController().navigate(UserPanelBottomSheetDirections.actionUserPanelBottomSheetToProfileFragment())
                1 -> findNavController().navigate(UserPanelBottomSheetDirections.actionUserPanelBottomSheetToWalletFragment())
                2 -> findNavController().navigate(UserPanelBottomSheetDirections.actionUserPanelBottomSheetToSettingsFragment())
            }

        }
    }
}