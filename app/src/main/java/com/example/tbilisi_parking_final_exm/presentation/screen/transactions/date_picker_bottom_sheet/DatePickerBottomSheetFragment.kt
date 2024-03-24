package com.example.tbilisi_parking_final_exm.presentation.screen.transactions.date_picker_bottom_sheet

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbilisi_parking_final_exm.databinding.FragmentDatePickerBottomSheetBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseBottomSheet
import com.example.tbilisi_parking_final_exm.presentation.model.transactions.DateType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


class DatePickerBottomSheetFragment :
    BaseBottomSheet<FragmentDatePickerBottomSheetBinding>(FragmentDatePickerBottomSheetBinding::inflate) {

    private val args: DatePickerBottomSheetFragmentArgs by navArgs()

    override fun bind() {
        setDatePicker()
    }

    override fun bindViewActionListeners() {
        binding.btnDone.setOnClickListener {
            navigateBackToTransactions()
        }
    }

    /* IMPLEMENTATION DETAILS */

    override fun bindObserves() {}

    private fun setDatePicker() = with(Calendar.getInstance()) {
        if (args.dateType == DateType.FROM_DATE) {
            binding.datePicker.maxDate = parseDate(args.maxDate).time - TimeUnit.DAYS.toMillis(1)
        } else {
            binding.datePicker.maxDate = timeInMillis
        }

        time = parseDate(args.currentDate)

        binding.datePicker.updateDate(
            get(Calendar.YEAR),
            get(Calendar.MONTH),
            get(Calendar.DAY_OF_MONTH)
        )
    }

    private fun navigateBackToTransactions() {
        val resultBundle = Bundle().apply {
            putString("date", getDateFromDatePicker())
            putString("dateIdentifier", args.dateType.name)
        }

        parentFragmentManager.setFragmentResult("transactionsRequestKey", resultBundle)

        findNavController().popBackStack()
    }

    private fun getDateFromDatePicker(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = Calendar.getInstance().run {
            with(binding.datePicker) {
                set(year, month, dayOfMonth)
            }

            dateFormat.format(time)
        }

        return date
    }

    private fun parseDate(date: String?): Date {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date!!)!!
    }
}