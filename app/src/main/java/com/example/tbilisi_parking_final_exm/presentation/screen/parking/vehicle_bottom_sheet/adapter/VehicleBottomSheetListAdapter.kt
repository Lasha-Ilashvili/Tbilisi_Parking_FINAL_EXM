package com.example.tbilisi_parking_final_exm.presentation.screen.parking.vehicle_bottom_sheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbilisi_parking_final_exm.databinding.ItemUserPanelBottomSheetBinding
import com.example.tbilisi_parking_final_exm.presentation.state.vehicle_bottom_sheet.VehicleBottomSheetState

class VehicleBottomSheetListAdapter :
    ListAdapter<VehicleBottomSheetState, VehicleBottomSheetListAdapter.BottomSheetItemViewHolder>(
        BottomSheetItemDiffUtil()
    ) {

    private var onItemClickListener: ((id: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (id: Int) -> Unit)  {
        onItemClickListener = listener
    }

    inner class BottomSheetItemViewHolder(private val binding: ItemUserPanelBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: VehicleBottomSheetState
        fun bind() {
            with(binding) {
                item = currentList[adapterPosition]
                icon.setImageResource(item.icon)
                tvTitle.text = item.title
                root.setOnClickListener {
                    onItemClickListener?.invoke(item.id)
                }
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

    class BottomSheetItemDiffUtil : DiffUtil.ItemCallback<VehicleBottomSheetState>() {
        override fun areItemsTheSame(
            oldItem: VehicleBottomSheetState,
            newItem: VehicleBottomSheetState
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: VehicleBottomSheetState,
            newItem: VehicleBottomSheetState
        ): Boolean {
            return oldItem == newItem
        }
    }
}