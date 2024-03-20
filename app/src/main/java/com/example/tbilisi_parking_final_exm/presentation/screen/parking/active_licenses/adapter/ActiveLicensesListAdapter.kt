package com.example.tbilisi_parking_final_exm.presentation.screen.parking.active_licenses.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.LicenseRecyclerItemBinding
import com.example.tbilisi_parking_final_exm.presentation.model.active_licenses.ActiveLicense

class ActiveLicensesListAdapter :
    ListAdapter<ActiveLicense, ActiveLicensesListAdapter.ActiveLicensesViewHolder>(
        ActiveLicensesDiffUtil
    ) {

    object ActiveLicensesDiffUtil : DiffUtil.ItemCallback<ActiveLicense>() {
        override fun areItemsTheSame(oldItem: ActiveLicense, newItem: ActiveLicense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ActiveLicense, newItem: ActiveLicense): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveLicensesViewHolder {
        return ActiveLicensesViewHolder(
            LicenseRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActiveLicensesViewHolder, position: Int) {
        holder.bind()
    }

    inner class ActiveLicensesViewHolder(private val binding: LicenseRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val activeLicense = currentList[adapterPosition]

            with(binding) {
                ivLicenseBackground.background.setTint(
                    ContextCompat.getColor(
                        itemView.context,
                        activeLicense.license.licenseType.backgroundColor
                    )
                )

                tvPeriod.text = itemView.context.getString(activeLicense.license.validity.toInt())
                tvLicenseType.text =
                    itemView.context.getString(activeLicense.license.licenseType.type)
                tvPrice.text = activeLicense.license.price.toString()
                tvRecDate.text = activeLicense.recDate
                ivLicense.setColorFilter(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.license_icon_tint
                    )
                )
            }
        }
    }
}