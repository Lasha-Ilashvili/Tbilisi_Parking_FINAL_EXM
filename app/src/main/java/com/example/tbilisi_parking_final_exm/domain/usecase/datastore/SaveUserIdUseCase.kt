package com.example.tbilisi_parking_final_exm.domain.usecase.datastore

import com.example.tbilisi_parking_final_exm.domain.repository.datastore.DataStoreRepository
import com.example.tbilisi_parking_final_exm.domain.user_data_key.PreferenceKeys
import javax.inject.Inject

class SaveUserIdUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(userId: Int) {
        dataStoreRepository.saveString(key = PreferenceKeys.userId, value = userId.toString())
    }
}