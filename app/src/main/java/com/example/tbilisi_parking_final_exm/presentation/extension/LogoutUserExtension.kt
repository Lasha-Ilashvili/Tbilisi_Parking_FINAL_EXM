package com.example.tbilisi_parking_final_exm.presentation.extension

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun Context.showAlertForLogout(logoutAction: () -> Unit) {
    val title = "Session Time Expired"
    val message = "You have to log out and sign up"
    val logoutButtonText = "Log Out"

    val alertDialog = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(logoutButtonText) { _, _ ->
            logoutAction.invoke()
        }
        .setCancelable(false)
        .create()
    alertDialog.show()
}