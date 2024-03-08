package com.example.tbilisi_parking_final_exm.data.repository.map

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.mapper.map.toDomain
import com.example.tbilisi_parking_final_exm.data.model.map.MarkerLocationDto
import com.example.tbilisi_parking_final_exm.domain.model.map.GetMarkerLocation
import com.example.tbilisi_parking_final_exm.domain.repository.map.MarkerLocationsRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

class MarkerLocationsRepositoryImpl @Inject constructor(
    private val moshi: Moshi,
    private val handleResponse: HandleResponse
) : MarkerLocationsRepository {


    override suspend fun getMarkerLocations(jsonString: String): List<GetMarkerLocation> {
        val adapter: JsonAdapter<List<MarkerLocationDto>> = moshi.adapter(
            Types.newParameterizedType(List::class.java, MarkerLocationDto::class.java)
        )

        return adapter.fromJson(jsonString)!!.map { dto ->
            dto.toDomain()
        }
    }
}