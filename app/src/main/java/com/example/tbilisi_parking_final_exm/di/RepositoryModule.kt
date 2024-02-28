package com.example.tbilisi_parking_final_exm.di

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.repository.sign_up.SignUpRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.service.sign_up.SignUpService
import com.example.tbilisi_parking_final_exm.domain.repository.sign_up.SignUpRepository
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
    fun provideSignUpRepository(
        signUpService: SignUpService,
        handleResponse: HandleResponse
    ): SignUpRepository {
        return SignUpRepositoryImpl(signUpService, handleResponse)
    }
}