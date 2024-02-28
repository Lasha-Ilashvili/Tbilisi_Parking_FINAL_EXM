package com.example.tbilisi_parking_final_exm.di

import com.example.tbilisi_parking_final_exm.domain.usecase.validator.EmailValidatorUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.validator.PasswordValidatorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideEmailValidatorUseCase(): EmailValidatorUseCase{
        return EmailValidatorUseCase()
    }

    @Provides
    @Singleton
    fun providePasswordValidatorUseCase(): PasswordValidatorUseCase{
        return PasswordValidatorUseCase()
    }
}