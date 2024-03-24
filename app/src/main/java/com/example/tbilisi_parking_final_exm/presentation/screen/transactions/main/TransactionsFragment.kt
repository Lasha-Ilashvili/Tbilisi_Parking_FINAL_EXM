package com.example.tbilisi_parking_final_exm.presentation.screen.transactions.main

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentTransactionsBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.transactions.TransactionsEvent
import com.example.tbilisi_parking_final_exm.presentation.model.transactions.DateType
import com.example.tbilisi_parking_final_exm.presentation.screen.transactions.main.adapter.TransactionsListAdapter
import com.example.tbilisi_parking_final_exm.presentation.state.transactions.TransactionsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class TransactionsFragment :
    BaseFragment<FragmentTransactionsBinding>(FragmentTransactionsBinding::inflate) {

    private val viewModel: TransactionsViewModel by viewModels()

    private lateinit var adapter: TransactionsListAdapter

    private lateinit var fromDate: String
    private lateinit var toDate: String

    override fun bind() {
        setRecycler()
        setInitialDate()
        setLayout()
        getTransactions()
    }

    override fun bindViewActionListeners() {
        setRefreshListener()

        with(binding) {
            fromDateLayout.root.setOnClickListener {
                findNavController().navigate(
                    TransactionsFragmentDirections.actionTransactionsFragmentToDatePickerBottomSheetFragment(
                        dateType = DateType.FROM_DATE,
                        currentDate = fromDate,
                        maxDate = toDate
                    )
                )
            }

            toDateLayout.root.setOnClickListener {
                findNavController().navigate(
                    TransactionsFragmentDirections.actionTransactionsFragmentToDatePickerBottomSheetFragment(
                        dateType = DateType.TO_DATE,
                        currentDate = toDate
                    )
                )
            }

            btnUpdate.setOnClickListener {
                getTransactions()
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setDate()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.transactionsState.collectLatest {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun setRecycler() {
        adapter = TransactionsListAdapter()
        binding.rvTransactions.adapter = adapter
    }

    private fun setInitialDate() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        with(Calendar.getInstance()) {
            toDate = dateFormat.format(time)

            add(Calendar.MONTH, -1)
            fromDate = dateFormat.format(time)
        }
    }

    private fun setLayout() = with(binding) {
        fromDateLayout.tvDate.text = fromDate
        fromDateLayout.tvDateType.text = getString(R.string.from_date)

        toDateLayout.tvDate.text = toDate
        toDateLayout.tvDateType.text = getString(R.string.to_date)
    }

    private fun getTransactions() {
        viewModel.onEvent(
            TransactionsEvent.GetTransactions(
                fromDate = fromDate,
                toDate = toDate
            )
        )
    }

    private fun setRefreshListener() = with(binding.transactionsSwipeRefresh) {
        setOnRefreshListener {
            isRefreshing = false

            getTransactions()
        }
    }

    private suspend fun handleState(transactionsState: TransactionsState) = with(binding) {
        transactionsState.data?.let {
            adapter.submitData(it)
        }
    }

    private fun setDate() {
        setFragmentResultListener("transactionsRequestKey") { _, bundle ->
            val date = bundle.getString("date")
            val dateIdentifier = bundle.getString("dateIdentifier")

            date?.let {
                if (dateIdentifier == DateType.TO_DATE.name) {
                    toDate = it
                } else {
                    fromDate = it
                }

                setLayout()
            }
        }
    }
}