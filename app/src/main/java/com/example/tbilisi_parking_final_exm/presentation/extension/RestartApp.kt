package com.example.tbilisi_parking_final_exm.presentation.extension

import android.content.Intent
import androidx.fragment.app.FragmentActivity

fun restartApp(activity: FragmentActivity) {
    // Create an Intent to start the main activity of your application
    val intent = activity.packageManager.getLaunchIntentForPackage(activity.packageName)
    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    // Restart the application by starting the main activity
    activity.startActivity(intent)
    activity.finish() // Optional: Finish the current activity if needed
}