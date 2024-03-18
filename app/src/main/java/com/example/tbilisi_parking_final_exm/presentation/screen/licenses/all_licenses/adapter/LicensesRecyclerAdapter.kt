package com.example.tbilisi_parking_final_exm.presentation.screen.licenses.all_licenses.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbilisi_parking_final_exm.databinding.LicenseItemBinding
import com.example.tbilisi_parking_final_exm.databinding.TitleItemBinding
import com.example.tbilisi_parking_final_exm.presentation.model.Licenses.License

class LicensesRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var licenses: List<License>

    var onClick: ((License.ZonalCard?) -> Unit)? = null

    private companion object {
        private const val TITLE = 0
        private const val CARDS = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TITLE
            else -> CARDS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE -> TitleViewHolder(
                TitleItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> LicensesViewHolder(
                LicenseItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun submitList(newList: List<License>) {
        licenses = newList
    }

    override fun getItemCount(): Int {
        return licenses.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LicensesViewHolder)
            holder.bind()
    }

    inner class TitleViewHolder(titleItemBinding: TitleItemBinding) :
        RecyclerView.ViewHolder(titleItemBinding.root)

    inner class LicensesViewHolder(private val binding: LicenseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivLicenseBackground.setOnClickListener {
                onClick?.invoke(licenses[adapterPosition].zonalCard)
            }
        }

        fun bind() = with(binding) {
            val card = licenses[adapterPosition]
            val zonalCard = card.zonalCard

            tvLicenseTitle.text = card.title

            zonalCard?.let {
                tvPeriod.text = it.period
                tvPrice.text = it.price.toString()
                ivLicenseBackground.background.setTint(Color.parseColor(it.backgroundColor))
                ivLicenseBackground.background.alpha = 99
                ivLicenseBackground.setColorFilter(Color.parseColor(it.backgroundColor))
            }
        }
    }
}