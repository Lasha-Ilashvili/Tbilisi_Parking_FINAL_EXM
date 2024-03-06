package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel_bottom_sheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbilisi_parking_final_exm.databinding.ItemUserPanelBottomSheetBinding
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel_bottom_sheet.UserPanelBottomSheetState

class BottomSheetListAdapter :
    ListAdapter<UserPanelBottomSheetState, BottomSheetListAdapter.BottomSheetItemViewHolder>(
        BottomSheetItemDiffUtil()
    ) {

    inner class BottomSheetItemViewHolder(private val binding: ItemUserPanelBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
            private lateinit var item: UserPanelBottomSheetState
        fun bind() {
            with(binding) {
                item = currentList[adapterPosition]
                icon.setImageResource(item.icon)
                tvTitle.text = item.title
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BottomSheetItemViewHolder(
        ItemUserPanelBottomSheetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: BottomSheetItemViewHolder, position: Int) {
        holder.bind()
    }



    class BottomSheetItemDiffUtil : DiffUtil.ItemCallback<UserPanelBottomSheetState>() {
        override fun areItemsTheSame(
            oldItem: UserPanelBottomSheetState,
            newItem: UserPanelBottomSheetState
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserPanelBottomSheetState,
            newItem: UserPanelBottomSheetState
        ): Boolean {
            return oldItem == newItem
        }
    }
}