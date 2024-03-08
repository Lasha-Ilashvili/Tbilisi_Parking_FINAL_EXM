package com.example.tbilisi_parking_final_exm.data.repository.map

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.data_source.map.LatLngDataSource
import com.example.tbilisi_parking_final_exm.data.extension.toLatLng
import com.example.tbilisi_parking_final_exm.data.mapper.map.toDomain
import com.example.tbilisi_parking_final_exm.data.model.map.MarkerLocationDto
import com.example.tbilisi_parking_final_exm.domain.model.map.GetMarkerLocation
import com.example.tbilisi_parking_final_exm.domain.repository.map.MarkerLocationsRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MarkerLocationsRepositoryImpl @Inject constructor(
    private val moshi: Moshi,
    private val latLngDataSource: LatLngDataSource
) : MarkerLocationsRepository {

    override fun getMarkerLocations(jsonString: String): Flow<Resource<List<GetMarkerLocation>>> {
        return flow {

            emit(Resource.Loading(loading = true))

            val adapter: JsonAdapter<List<MarkerLocationDto>> = moshi.adapter(
                Types.newParameterizedType(List::class.java, MarkerLocationDto::class.java)
            )

            val markers: MutableList<GetMarkerLocation> = mutableListOf()

            adapter.fromJson(jsonString)!!.forEach { dto ->
                val latLngResponse = latLngDataSource(dto.address)

                try {
                    if (latLngResponse is Resource.Success) {
                        markers.add(dto.toDomain(latLngResponse.data.toLatLng()))
                    }
                } catch (e: Throwable) {
                    emit(Resource.Loading(loading = false))
                    emit(Resource.Error(errorMessage = e.message ?: ""))
                }
            }

            emit(Resource.Loading(loading = false))

            emit(Resource.Success(data = markers.toList()))
        }
    }
}