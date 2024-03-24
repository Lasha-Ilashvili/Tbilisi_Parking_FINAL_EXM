package com.example.tbilisi_parking_final_exm.domain.usecase.transactions

import com.example.tbilisi_parking_final_exm.domain.repository.transactions.TransactionsRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {

    suspend operator fun invoke(userId: Int, fromDate: String, toDate: String) =
        transactionsRepository.getTransactions(
            userId = userId,
            fromDate = fromDate,
            toDate = addOneDay(toDate)
        )


    private fun addOneDay(dateString: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance().apply {
            time = format.parse(dateString)!!
            add(Calendar.DAY_OF_MONTH, 1)
        }
        return format.format(calendar.time)
    }
}