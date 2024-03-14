package com.example.tbilisi_parking_final_exm.presentation.screen.cards.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbilisi_parking_final_exm.databinding.CardItemBinding
import com.example.tbilisi_parking_final_exm.databinding.TitleItemBinding
import com.example.tbilisi_parking_final_exm.presentation.model.cards.Card

class CardsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var cards: List<Card>

    var onClick: ((Card.ZonalCard) -> Unit)? = null

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

            else -> CardsViewHolder(
                CardItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun submitList(newList: List<Card>) {
        cards = newList
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CardsViewHolder)
            holder.bind()
    }

    inner class TitleViewHolder(titleItemBinding: TitleItemBinding) :
        RecyclerView.ViewHolder(titleItemBinding.root)

    inner class CardsViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivCardBackground.setOnClickListener {
                onClick?.invoke(cards[adapterPosition].zonalCard)
            }
        }

        fun bind() = with(binding) {
            val card = cards[adapterPosition]
            val zonalCard = card.zonalCard

            tvCardTitle.text = card.title
            tvPeriod.text = zonalCard.period
            tvPrice.text = zonalCard.price.toString()
        }
    }
}