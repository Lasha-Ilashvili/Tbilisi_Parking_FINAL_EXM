package com.example.tbilisi_parking_final_exm.di

import com.example.tbilisi_parking_final_exm.BuildConfig
import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.service.LogInService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    private const val BASE_URL = "https://reqres.in/api/"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            client.addInterceptor(httpLoggingInterceptor)
        }

        // Add basic authentication interceptor
        client.addInterceptor { chain ->
            val original = chain.request()

            // Add basic authentication header
            val authenticatedRequest = original.newBuilder()
                .header("Authorization", Credentials.basic("park-pro-client-android-app", "secret"))
                .build()

            chain.proceed(authenticatedRequest)
        }

        return client.build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): MoshiConverterFactory {
        return MoshiConverterFactory.create(
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        )
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }


    @Singleton
    @Provides
    fun provideLogInService(retrofit: Retrofit): LogInService {
        return retrofit.create(LogInService::class.java)
    }

    @Singleton
    @Provides
    fun provideHandleResponse(): HandleResponse {
        return HandleResponse()
    }
}