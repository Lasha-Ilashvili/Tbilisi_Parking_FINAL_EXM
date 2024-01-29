package com.example.tbilisi_parking_final_exm.presentation.extension

import android.view.View
import android.widget.Toast

fun View.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}