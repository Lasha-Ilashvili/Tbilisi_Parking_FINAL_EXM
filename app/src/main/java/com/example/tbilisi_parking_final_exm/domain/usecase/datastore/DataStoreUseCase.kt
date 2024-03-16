package com.example.tbilisi_parking_final_exm.domain.usecase.datastore

import javax.inject.Inject

data class DataStoreUseCase @Inject constructor(
    val clearDataStore: ClearDataStoreUseCase,

    val saveAccessToken: SaveAccessTokenUseCase,
    val getAccessToken: GetAccessTokenUseCase,

    val saveRefreshToken: SaveRefreshTokenUseCase,
    val getRefreshToken: GetRefreshTokenUseCase,

    val saveUserId: SaveUserIdUseCase,
    val getUserId: GetUserIdUseCase,

    val saveSession: SaveSessionUseCase,
    val getSession: GetSessionUseCase
) {

}