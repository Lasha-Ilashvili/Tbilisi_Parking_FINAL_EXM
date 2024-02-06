package com.example.tbilisi_parking_final_exm.data.common

import kotlinx.coroutines.flow.flow
import retrofit2.Response

class HandleResponse() {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(loading = true))
        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Resource.Loading(loading = false))
                emit(Resource.Success(data = body))
            } else {
                emit(Resource.Loading(loading = false))
                emit(Resource.Error(errorMessage = response.errorBody()?.string() ?: ""))
            }
        } catch (e: Throwable) {
            emit(Resource.Loading(loading = false))
            emit(Resource.Error(errorMessage = e.message ?: ""))
        }
    }
}