package com.example.tbilisi_parking_final_exm.domain.usecase.datastore

import com.example.tbilisi_parking_final_exm.domain.repository.datastore.DataStoreRepository
import com.example.tbilisi_parking_final_exm.domain.user_data_key.PreferenceKeys
import javax.inject.Inject

class SaveSecondStringUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(secondString: String) {
        dataStoreRepository.saveString(key = PreferenceKeys.refreshToken, value = secondString)
    }
}