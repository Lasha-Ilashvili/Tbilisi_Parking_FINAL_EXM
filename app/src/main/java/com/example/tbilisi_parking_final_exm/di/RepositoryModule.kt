package com.example.tbilisi_parking_final_exm.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.repository.datastore.DataStoreRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.log_in.LogInRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.profile.ProfileRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.sign_up.SignUpRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.service.log_in.LogInService
import com.example.tbilisi_parking_final_exm.data.service.profile.ProfileService
import com.example.tbilisi_parking_final_exm.data.service.sign_up.SignUpService
import com.example.tbilisi_parking_final_exm.domain.repository.datastore.DataStoreRepository
import com.example.tbilisi_parking_final_exm.domain.repository.log_in.LogInRepository
import com.example.tbilisi_parking_final_exm.domain.repository.profile.ProfileRepository
import com.example.tbilisi_parking_final_exm.data.data_source.map.LatLngDataSource
import com.example.tbilisi_parking_final_exm.data.repository.map.MarkerLocationsRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.service.map.LatLngService
import com.example.tbilisi_parking_final_exm.domain.repository.map.MarkerLocationsRepository
import com.example.tbilisi_parking_final_exm.domain.repository.sign_up.SignUpRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLogInRepository(
        logInService: LogInService,
        handleResponse: HandleResponse
    ): LogInRepository {
        return LogInRepositoryImpl(handleResponse = handleResponse, logInService = logInService)
    }

    @Singleton
    @Provides
    fun provideSignUpRepository(
        signUpService: SignUpService,
        handleResponse: HandleResponse
    ): SignUpRepository {
        return SignUpRepositoryImpl(signUpService = signUpService, handleResponse = handleResponse)
    }


    @Provides
    @Singleton
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
        return DataStoreRepositoryImpl(datastore = dataStore)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        handleResponse: HandleResponse,
        profileService: ProfileService
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            handleResponse = handleResponse,
            profileService = profileService
        )
    }

    @Singleton
    @Provides
    fun provideLatLngDataSource(
        latLngService: LatLngService,
        handleResponse: HandleResponse
    ): LatLngDataSource {
        return LatLngDataSource(
            latLngService = latLngService,
            handleResponse = handleResponse
        )
    }

    @Singleton
    @Provides
    fun provideMarkerLocationsRepository(
        moshi: Moshi,
        latLngDataSource: LatLngDataSource
    ): MarkerLocationsRepository {
        return MarkerLocationsRepositoryImpl(
            moshi = moshi,
            latLngDataSource = latLngDataSource
        )
    }

}