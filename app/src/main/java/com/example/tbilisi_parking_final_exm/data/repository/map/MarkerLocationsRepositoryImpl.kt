package com.example.tbilisi_parking_final_exm.data.repository.map

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.mapper.map.toDomain
import com.example.tbilisi_parking_final_exm.data.model.map.MarkerLocationDto
import com.example.tbilisi_parking_final_exm.data.service.map.LatLngService
import com.example.tbilisi_parking_final_exm.domain.model.map.GetMarkerLocation
import com.example.tbilisi_parking_final_exm.domain.repository.map.MarkerLocationsRepository
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.json.JSONObject
import javax.inject.Inject

class MarkerLocationsRepositoryImpl @Inject constructor(
    private val moshi: Moshi,
    private val latLngService: LatLngService,
    private val handleResponse: HandleResponse
) : MarkerLocationsRepository {


    override suspend fun getMarkerLocations(jsonString: String): List<GetMarkerLocation> {
        val adapter: JsonAdapter<List<MarkerLocationDto>> = moshi.adapter<List<MarkerLocationDto>?>(
            Types.newParameterizedType(List::class.java, MarkerLocationDto::class.java)
        )

        val markers: MutableList<GetMarkerLocation> = mutableListOf()

        adapter.fromJson(jsonString)!!.forEach { dto ->
            locationNameToLatLng(dto.address)?.let {
                markers.add(dto.toDomain(it))
            }
        }

        return markers
    }

    private suspend fun locationNameToLatLng(address: String): LatLng? {

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