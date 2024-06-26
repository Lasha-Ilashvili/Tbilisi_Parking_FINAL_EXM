package com.example.tbilisi_parking_final_exm.data.mapper.base

import com.example.tbilisi_parking_final_exm.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun <Dto : Any, Domain : Any> Flow<Resource<Dto>>.asResource(
    onSuccess: suspend (Dto) -> Domain,
): Flow<Resource<Domain>> {
    return this.map {
        when (it) {
            is Resource.Success -> Resource.Success(data = onSuccess.invoke(it.data))
            is Resource.Error -> Resource.Error(errorMessage = it.errorMessage)
            is Resource.Loading -> Resource.Loading(loading = it.loading)
            is Resource.SessionCompleted -> Resource.SessionCompleted(sessionIsCompleted = it.sessionIsCompleted)
        }
    }
}
