package com.example.tbilisi_parking_final_exm.data.common


import com.example.tbilisi_parking_final_exm.data.extension.parseErrorMessage
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class HandleResponse() {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(loading = true))
        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Resource.Success(data = body))
            } else {
                val errorCode = response.code()
                if(errorCode == 401){
                    emit(Resource.Error(errorMessage = "Unauthorized access"))
                } else {
                    emit(Resource.Error(errorMessage = response.errorBody().parseErrorMessage()))
                }
            }
        } catch (e: Throwable) {
            emit(Resource.Error(errorMessage = e.message ?: ""))
        }
        emit(Resource.Loading(loading = false))
    }

    suspend fun <T : Any> safeApiCallWithoutFlow(call: suspend () -> Response<T>): Resource<T> =
        try {
            val response = call()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                Resource.Success(data = body)
            } else {
                Resource.Error(errorMessage = response.errorBody().toString())
            }
        } catch (e: Throwable) {
            Resource.Error(errorMessage = e.message ?: "")
        }
}

