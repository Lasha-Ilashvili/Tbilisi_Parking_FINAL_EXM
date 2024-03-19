package com.example.tbilisi_parking_final_exm.presentation.screen.fines

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbilisi_parking_final_exm.databinding.FragmentFinesBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import kotlinx.coroutines.launch


class FinesFragment : BaseFragment<FragmentFinesBinding>(FragmentFinesBinding::inflate) {

    private val viewModel: FinesViewModel by viewModels()




    override fun bind() {


    }


    override fun bindViewActionListeners() {
        binding.btnStartTimer.setOnClickListener {
            viewModel.startTimer("2024-03-19 11:25:18")
        }

        binding.btnEndTimer.setOnClickListener {
            viewModel.stopTimer()
        }
    }

    override fun bindObserves() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.finesState.collect{
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: String) {
        binding.tvTitle.text = state
    }








}