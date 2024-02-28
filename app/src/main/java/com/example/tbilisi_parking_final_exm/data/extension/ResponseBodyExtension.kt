package com.example.tbilisi_parking_final_exm.data.extension

import okhttp3.ResponseBody
import org.json.JSONObject

fun ResponseBody?.parseErrorMessage(): String {
    return this?.string()?.let {
        JSONObject(it).optString("message", it)
    } ?: ""
}