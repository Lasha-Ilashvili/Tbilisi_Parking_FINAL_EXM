package com.example.tbilisi_parking_final_exm.presentation.screen.transactions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tbilisi_parking_final_exm.databinding.TransactionItemBinding
import com.example.tbilisi_parking_final_exm.presentation.model.transactions.Transaction

class TransactionsListAdapter :
    PagingDataAdapter<Transaction.TransactionItem, TransactionsListAdapter.TransactionsViewHolder>(
        TransactionsDiffUtil
    ) {

    object TransactionsDiffUtil : DiffUtil.ItemCallback<Transaction.TransactionItem>() {
        override fun areItemsTheSame(
            oldItem: Transaction.TransactionItem,
            newItem: Transaction.TransactionItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Transaction.TransactionItem,
            newItem: Transaction.TransactionItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        return TransactionsViewHolder(
            TransactionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        holder.bind()
    }

    inner class TransactionsViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            getItem(bindingAdapterPosition)?.let { transaction ->
                with(binding) {
                    tvRecDate.text = transaction.recDate
                    tvTransactionStatus.text = transaction.transactionStatus
                    tvTransactionType.text =
                        itemView.context.getString(transaction.transactionType.typeName)
                    tvAmount.text = transaction.amount.toString()
                }
            }
        }
    }
}