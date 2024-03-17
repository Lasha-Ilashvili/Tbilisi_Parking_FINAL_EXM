package com.example.tbilisi_parking_final_exm.di


import com.example.tbilisi_parking_final_exm.BuildConfig
import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.service.add_vehicle.AddVehicleService
import com.example.tbilisi_parking_final_exm.data.service.edit_vehicle.EditVehicleService
import com.example.tbilisi_parking_final_exm.data.service.get_vehicle.GetAllVehicleService
import com.example.tbilisi_parking_final_exm.data.service.log_in.LogInService
import com.example.tbilisi_parking_final_exm.data.service.map.LatLngService
import com.example.tbilisi_parking_final_exm.data.service.sign_up.SignUpService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.profile.ProfileService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance.AddToBalanceService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance.DeleteRememberedCardService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance.RememberCardService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.main.GetBalanceService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.main.RememberedCardsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideMoshiFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create(
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        )
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authTokenFlow: Flow<String?>
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            client.addInterceptor(httpLoggingInterceptor)
        }
        client.addInterceptor { chain ->
            val authToken = runBlocking { authTokenFlow.first() }
            val newRequest = if (!authToken.isNullOrBlank()) {
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $authToken")
                    .build()
            } else {
                chain.request()
            }
            chain.proceed(newRequest)
        }
        return client.build()
    }

    @Provides
    @Singleton
    @Named("OkHttpClientForLogin")
    fun provideOkHttpClientForLogIn(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
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
    @Named("retrofitForLogin")
    fun provideRetrofitClientForLogIn(
        @Named("OkHttpClientForLogin") okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
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

    @Provides
    @Singleton
    @Named("retrofitForMap")
    fun provideRetrofitClientForMap(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_MAP)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideLatLngService(@Named("retrofitForMap") retrofit: Retrofit): LatLngService {
        return retrofit.create(LatLngService::class.java)
    }

    @Singleton
    @Provides
    fun provideLogInService(@Named("retrofitForLogin") retrofit: Retrofit): LogInService {

        return retrofit.create(LogInService::class.java)
    }


    @Singleton
    @Provides
    fun provideHandleResponse(): HandleResponse {
        return HandleResponse()
    }

    @Singleton
    @Provides
    fun provideSignUpService(@Named("retrofitForLogin") retrofit: Retrofit): SignUpService {
        return retrofit.create(SignUpService::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileService(retrofit: Retrofit): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }

    @Singleton
    @Provides
    fun provideAddVehicleService(retrofit: Retrofit): AddVehicleService {
        return retrofit.create(AddVehicleService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetAllVehicle(retrofit: Retrofit): GetAllVehicleService {
        return retrofit.create(GetAllVehicleService::class.java)
    }

    @Singleton
    @Provides
    fun provideEditVehicle(retrofit: Retrofit): EditVehicleService {
        return retrofit.create(EditVehicleService::class.java)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun provideRememberCardService(retrofit: Retrofit): RememberCardService {
        return retrofit.create(RememberCardService::class.java)
    }

    @Singleton
    @Provides
    fun provideAddToBalanceService(retrofit: Retrofit): AddToBalanceService {
        return retrofit.create(AddToBalanceService::class.java)
    }

    @Singleton
    @Provides
    fun provideDeleteCardService(retrofit: Retrofit): DeleteRememberedCardService {
        return retrofit.create(DeleteRememberedCardService::class.java)
    }

    @Singleton
    @Provides
    fun provideRememberedCardsService(retrofit: Retrofit): RememberedCardsService {
        return retrofit.create(RememberedCardsService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetBalanceService(retrofit: Retrofit): GetBalanceService {
        return retrofit.create(GetBalanceService::class.java)
    }
}