package com.example.tbilisi_parking_final_exm.presentation.screen.transactions

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbilisi_parking_final_exm.databinding.FragmentTransactionsBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.transactions.TransactionsEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.screen.transactions.adapter.TransactionsListAdapter
import com.example.tbilisi_parking_final_exm.presentation.state.transactions.TransactionsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TransactionsFragment :
    BaseFragment<FragmentTransactionsBinding>(FragmentTransactionsBinding::inflate) {

    private val viewModel: TransactionsViewModel by viewModels()

    override fun bind() {
        viewModel.onEvent(TransactionsEvent.GetTransactions)
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.transactionsState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    override fun bindViewActionListeners() {}

    private fun handleState(transactionsState: TransactionsState) = with(binding) {
        progressBar.root.visibility = if (transactionsState.isLoading) VISIBLE else GONE

        transactionsState.errorMessage?.let {
            root.showToast(it)
            viewModel.onEvent(TransactionsEvent.ResetErrorMessage)
        }

        transactionsState.data?.let {
            println(it)
            rvTransactions.adapter = TransactionsListAdapter().apply {
                submitList(it)
            }
        }
    }
}