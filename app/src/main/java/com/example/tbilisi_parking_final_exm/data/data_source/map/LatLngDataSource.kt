package com.example.tbilisi_parking_final_exm.data.data_source.map

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.service.map.LatLngService
import okhttp3.ResponseBody
import javax.inject.Inject

class LatLngDataSource @Inject constructor(
    private val latLngService: LatLngService,
    private val handleResponse: HandleResponse
) {

    suspend operator fun invoke(address: String): Resource<ResponseBody> {
        return handleResponse.safeApiCallWithoutFlow {
            latLngService.getLatLng(address)
        }
    }
}