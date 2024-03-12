package com.example.tbilisi_parking_final_exm.presentation.screen.parking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbilisi_parking_final_exm.databinding.ItemParkingVehicleLayoutBinding
import com.example.tbilisi_parking_final_exm.presentation.model.vehicle.vehicle.Vehicle

class ParkingVehiclesListAdapter :
    ListAdapter<Vehicle, ParkingVehiclesListAdapter.VehicleViewHolder>(VehiclesDiffUtil()) {

    inner class VehicleViewHolder(private val binding: ItemParkingVehicleLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
            private lateinit var item: Vehicle
        fun bind() {
            item = currentList[adapterPosition]
            with(binding){
                tvVehicleName.text = item.name
                tvVehiclePlateNumber.text = item.plateNumber
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VehicleViewHolder(
        ItemParkingVehicleLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind()
    }

    class VehiclesDiffUtil : DiffUtil.ItemCallback<Vehicle>() {
        override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return oldItem == newItem
        }
    }
}