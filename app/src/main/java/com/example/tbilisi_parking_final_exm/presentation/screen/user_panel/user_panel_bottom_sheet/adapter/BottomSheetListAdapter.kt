package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.user_panel_bottom_sheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbilisi_parking_final_exm.databinding.ItemBottomSheetBinding
import com.example.tbilisi_parking_final_exm.presentation.state.user_panel.user_panel_bottom_sheet.UserPanelBottomSheetState

class BottomSheetListAdapter :
    ListAdapter<UserPanelBottomSheetState, BottomSheetListAdapter.BottomSheetItemViewHolder>(
        BottomSheetItemDiffUtil()
    ) {

    private var onItemClickListener: ((id: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (id: Int) -> Unit)  {
        onItemClickListener = listener
    }

    inner class BottomSheetItemViewHolder(private val binding: ItemBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
            private lateinit var item: UserPanelBottomSheetState
        fun bind() {
            with(binding) {
                item = currentList[bindingAdapterPosition]
                icon.setImageResource(item.icon)
                tvTitle.text = item.title
                root.setOnClickListener {
                    onItemClickListener?.invoke(item.id)
                }
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BottomSheetItemViewHolder(
        ItemBottomSheetBinding.inflate(
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