package com.example.tbilisi_parking_final_exm.presentation.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun View.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun View.hideKeyboard(){
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}