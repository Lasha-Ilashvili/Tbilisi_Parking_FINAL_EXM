package com.example.tbilisi_parking_final_exm.data.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tbilisi_parking_final_exm.domain.repository.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val datastore: DataStore<Preferences>,
) : DataStoreRepository {


    override suspend fun saveString(key: Preferences.Key<String>, value: String) {
        datastore.edit { settings ->
            settings[key] = value
        }
    }


    override fun readString(key: Preferences.Key<String>): Flow<String> {
        return datastore.data.map { preferences ->
            preferences[key] ?: ""
        }
    }


    override suspend fun clear() {
        datastore.edit {
            it.clear()
        }
    }
}