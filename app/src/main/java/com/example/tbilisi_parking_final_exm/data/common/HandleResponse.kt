package com.example.tbilisi_parking_final_exm.data.common

import android.util.Log.d
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class HandleResponse() {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        // emit(Resource.Loading(loading = true))
        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                //    emit(Resource.Loading(loading = false))
                emit(Resource.Success(data = body))
            } else {
                d("CHECK_UNSUCCESSFUL", response.errorBody()?.string() ?: "")
//                emit(Resource.Loading(loading = false))
                emit(Resource.Error(errorMessage = response.errorBody()?.string() ?: ""))
            }
        } catch (e: Throwable) {
            d("CHECK_ERROR", e.message ?: "")
//            emit(Resource.Loading(loading = false))
            emit(Resource.Error(errorMessage = e.message ?: ""))
        }
    }
}