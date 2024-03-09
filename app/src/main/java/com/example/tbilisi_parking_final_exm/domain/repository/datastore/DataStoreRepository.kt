package com.example.tbilisi_parking_final_exm.domain.repository.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveString(key: Preferences.Key<String>, value: String)

    fun readString(key: Preferences.Key<String>): Flow<String>

    suspend fun clear()
}