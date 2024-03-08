package com.example.tbilisi_parking_final_exm.data.repository.map

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.service.map.LatLngService
import com.example.tbilisi_parking_final_exm.domain.repository.map.LatLngRepository
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject
import javax.inject.Inject

class LatLngRepositoryImpl @Inject constructor(
    private val latLngService: LatLngService,
    private val handleResponse: HandleResponse
) : LatLngRepository {

    override suspend fun locationNameToLatLng(address: String): LatLng? {

        try {
            val response = latLngService.getLatLng(address)

            if (response.isSuccessful) {
                response.body()?.string()?.let { jsonString ->
                    if (jsonString.isNotEmpty()) {
                        val locationObject = JSONObject(jsonString)
                            .getJSONArray("results").getJSONObject(0)
                            .getJSONObject("geometry").getJSONObject("location")

                        return LatLng(
                            locationObject.getDouble("lat"),
                            locationObject.getDouble("lng")
                        )
                    }
                }
            }
        } catch (e: Throwable) {
            return null
        }

        return null
    }
}