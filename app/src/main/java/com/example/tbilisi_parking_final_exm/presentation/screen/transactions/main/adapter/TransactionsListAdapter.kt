package com.example.tbilisi_parking_final_exm.presentation.screen.transactions.main.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tbilisi_parking_final_exm.databinding.TransactionItemBinding
import com.example.tbilisi_parking_final_exm.presentation.model.transactions.Transaction
import com.example.tbilisi_parking_final_exm.presentation.model.transactions.Transaction.TransactionItem.TransactionType.BUY_LICENSE_BY_CARD
import com.example.tbilisi_parking_final_exm.presentation.model.transactions.Transaction.TransactionItem.TransactionType.BUY_LICENSE_FROM_BALANCE
import com.example.tbilisi_parking_final_exm.presentation.model.transactions.Transaction.TransactionItem.TransactionType.DEPOSIT_FROM_CARD
import com.example.tbilisi_parking_final_exm.presentation.model.transactions.Transaction.TransactionItem.TransactionType.END_PARKING

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

                    setTransactionType(transaction)
                }
            }
        }

        private fun setTransactionType(transaction: Transaction.TransactionItem) =
            with(transaction) {
                resetLayouts()

                when (this.transactionType) {
                    END_PARKING -> {
                        with(binding.endParkingLayout) {
                            root.visibility = VISIBLE
                            tvCarName.text = car?.name
                            tvPlateNumber.text = car?.plateNumber
                            tvParkingStation.text = parkingStation?.externalId
                            tvAddress.text = parkingStation?.address
                        }
                    }

                    DEPOSIT_FROM_CARD -> {
                        with(binding.depositFromCardLayout) {
                            root.visibility = VISIBLE
                            tvCardNumber.text = cardNumber
                        }
                    }


                    BUY_LICENSE_BY_CARD -> {
                        with(binding.buyLicenseLayout) {
                            tvCardNumber.visibility = VISIBLE
                            tvCardNumberStatic.visibility = VISIBLE
                            root.visibility = VISIBLE
                            tvCarName.text = car?.name
                            tvPlateNumber.text = car?.plateNumber
                            tvCardNumber.text = cardNumber
                            tvLicenseType.text = itemView.context.getString(license!!.type)
                        }
                    }

                    BUY_LICENSE_FROM_BALANCE -> {
                        with(binding.buyLicenseLayout) {
                            root.visibility = VISIBLE
                            tvCarName.text = car?.name
                            tvCardNumber.visibility = GONE
                            tvCardNumberStatic.visibility = GONE
                            tvPlateNumber.text = car?.plateNumber
                            tvLicenseType.text = itemView.context.getString(license!!.type)
                        }
                    }
                }
            }

        private fun resetLayouts() = with(binding) {
            depositFromCardLayout.root.visibility = GONE
            endParkingLayout.root.visibility = GONE
            buyLicenseLayout.root.visibility = GONE
        }
    }
}