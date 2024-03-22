package com.example.tbilisi_parking_final_exm.presentation.extension

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import com.example.tbilisi_parking_final_exm.R
import com.google.android.material.snackbar.Snackbar


fun View.showSnackBar(message: String) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(context.getColor(R.color.red))

    snackBar.view.layoutParams.apply {
        this as FrameLayout.LayoutParams

        val margin = context.resources.getDimensionPixelSize(R.dimen.margin_large)

        gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        setMargins(0, margin, 0, 0)
        width = FrameLayout.LayoutParams.WRAP_CONTENT

        snackBar.view.layoutParams = this
    }

    snackBar.show()
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}