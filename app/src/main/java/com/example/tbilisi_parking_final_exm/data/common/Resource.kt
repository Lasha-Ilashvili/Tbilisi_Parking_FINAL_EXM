package com.example.tbilisi_parking_final_exm.data.common

sealed class Resource<out T : Any> {
    data class Success<out D : Any>(val data: D) : Resource<D>()
    data class Error(val errorMessage: String) : Resource<Nothing>()
    data class Loading(val loading: Boolean) : Resource<Nothing>()
}