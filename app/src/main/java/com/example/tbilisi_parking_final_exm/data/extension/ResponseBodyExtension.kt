package com.example.tbilisi_parking_final_exm.data.extension

import com.google.android.gms.maps.model.LatLng
import okhttp3.ResponseBody
import org.json.JSONObject

fun ResponseBody?.parseErrorMessage(): String {
    return this?.string()?.let {
        JSONObject(it).optString("message", it)
        JSONObject(it).optString("error", it)
    } ?: ""
}

fun ResponseBody.toLatLng(): LatLng {
    string().apply {
        val locationObject = JSONObject(this)
            .getJSONArray("results").getJSONObject(0)
            .getJSONObject("geometry").getJSONObject("location")

        return LatLng(
            locationObject.getDouble("lat"),
            locationObject.getDouble("lng")
        )
    }
}