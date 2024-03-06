package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel_bottom_sheet

import com.example.tbilisi_parking_final_exm.databinding.FragmentUserPanelBottomSheetBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserPanelBottomSheet : BaseBottomSheet<FragmentUserPanelBottomSheetBinding>(FragmentUserPanelBottomSheetBinding::inflate){
    override fun bind() {
        println("bottomsheet")
    }

    override fun bindViewActionListeners() {
    }

    override fun bindObserves() {
    }
}