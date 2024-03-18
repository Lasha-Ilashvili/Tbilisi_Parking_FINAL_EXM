package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.wallet.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbilisi_parking_final_exm.databinding.UserCardItemBinding
import com.example.tbilisi_parking_final_exm.presentation.model.user_panel.wallet.cards.UserCard

class UserCardsListAdapter :
    ListAdapter<UserCard, UserCardsListAdapter.UserCardsViewHolder>(UserCardsDiffUtil) {

    object UserCardsDiffUtil : DiffUtil.ItemCallback<UserCard>() {
        override fun areItemsTheSame(oldItem: UserCard, newItem: UserCard): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserCard, newItem: UserCard): Boolean {
            return oldItem == newItem
        }
    }

    var onClick: ((UserCard) -> Unit)? = null
    var onDeleteClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCardsViewHolder {
        return UserCardsViewHolder(
            UserCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserCardsViewHolder, position: Int) {
        holder.bind()
    }

    inner class UserCardsViewHolder(private val binding: UserCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                root.setOnClickListener {
                    onClick?.invoke(currentList[adapterPosition])
                }

                btnDelete.setOnClickListener {
                    onDeleteClick?.invoke(currentList[adapterPosition].id)
                }
            }
        }

        fun bind() {
            binding.tvCardNumber.text = getMaskedCardNumber(currentList[adapterPosition])
        }

        private fun getMaskedCardNumber(card: UserCard): CharSequence = with(card) {
            val maskedPart = cardNumber.substring(cardNumber.length - 8, cardNumber.length - 4)
                .replace(Regex("\\d"), "*")
            val lastFourDigits = cardNumber.substring(cardNumber.length - 4)

            "$maskedPart $lastFourDigits"
        }
    }
}