package com.example.tbilisi_parking_final_exm.presentation.extension

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(inputDate:String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM, EEEE, HH:mm", Locale.getDefault())
    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date!!)
}