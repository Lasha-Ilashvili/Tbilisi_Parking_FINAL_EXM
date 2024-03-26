package com.example.tbilisi_parking_final_exm.presentation.extension

import android.content.Context




fun Int.jsonToString(context: Context): String {
    return context.resources.openRawResource(this).bufferedReader().use {
        it.readText()
    }
}