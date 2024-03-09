package com.example.tbilisi_parking_final_exm.domain.usecase.datastore

import com.example.tbilisi_parking_final_exm.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

class ClearDataStoreUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() {
        dataStoreRepository.clear()
    }
}