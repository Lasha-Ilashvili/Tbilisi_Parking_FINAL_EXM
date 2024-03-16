package com.example.tbilisi_parking_final_exm.presentation.extension

import android.app.AlertDialog
import android.content.Context

fun Context.showAlertDialog(
    title: String,
    message: String,
    positiveButtonText: String,
    negativeButtonText: String,
    positiveButtonClickAction: () -> Unit
) = with(AlertDialog.Builder(this)) {
    setTitle(title)
    setMessage(message)

    setPositiveButton(positiveButtonText) { _, _ ->
        positiveButtonClickAction.invoke()
    }

    setNegativeButton(negativeButtonText) { dialog, _ ->
        dialog.dismiss()
    }

    create().show()
}