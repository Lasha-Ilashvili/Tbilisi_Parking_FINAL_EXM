package com.example.tbilisi_parking_final_exm.data.common


sealed class Resource<out D : Any> {
    data class Success<out D : Any>(val data: D) : Resource<D>()
    data class Error<out D : Any>(val errorMessage: String) : Resource<D>()
    data class Loading<Nothing : Any>(val loading: Boolean) : Resource<Nothing>()
    data class SessionCompleted<Nothing: Any>(val sessionIsCompleted: Boolean) : Resource<Nothing>()
}

