package com.example.tbilisi_parking_final_exm.di

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.repository.log_in.LogInRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.service.LogInService
import com.example.tbilisi_parking_final_exm.domain.repository.log_in.LogInRepository
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
    ) : LogInRepository {
        return LogInRepositoryImpl(handleResponse = handleResponse, logInService = logInService)
    }
}