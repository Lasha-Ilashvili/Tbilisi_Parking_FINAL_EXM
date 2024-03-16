package com.example.tbilisi_parking_final_exm.di


import com.example.tbilisi_parking_final_exm.BuildConfig
import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.service.parking.add_vehicle.AddVehicleService
import com.example.tbilisi_parking_final_exm.data.service.parking.edit_vehicle.EditVehicleService
import com.example.tbilisi_parking_final_exm.data.service.parking.get_vehicle.GetAllVehicleService
import com.example.tbilisi_parking_final_exm.data.service.log_in.LogInService
import com.example.tbilisi_parking_final_exm.data.service.map.LatLngService
import com.example.tbilisi_parking_final_exm.data.service.profile.ProfileService
import com.example.tbilisi_parking_final_exm.data.service.refresh_token.RefreshTokenService
import com.example.tbilisi_parking_final_exm.data.service.sign_up.SignUpService
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.ClearDataStoreUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.GetRefreshTokenUseCase
import com.example.tbilisi_parking_final_exm.domain.usecase.datastore.SaveAccessTokenUseCase
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
        authTokenFlow: Flow<String?>,
        refreshTokenService: RefreshTokenService,
        saveAccessTokenUseCase: SaveAccessTokenUseCase,
        getRefreshTokenUseCase: GetRefreshTokenUseCase,
        clearDataStoreUseCase: ClearDataStoreUseCase
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

//            handle accessToken refreshing processes
            val response = chain.proceed(newRequest)
            if (response.code == 401) {

                if (!authToken.isNullOrBlank()) {
                    val refreshToken = runBlocking { getRefreshTokenUseCase().first() }
                    val refreshedToken = runBlocking {
                        refreshTokenService.refreshToken(
                            "refresh_token",
                            refreshToken
                        )
                    }

                    if (refreshedToken.isSuccessful) {
                        val newToken = refreshedToken.body()?.accessToken
                        runBlocking { saveAccessTokenUseCase(newToken!!) }
                        val readNewAuthToken = runBlocking { authTokenFlow.first() }

                        val newRequestWithNewToken = if (!readNewAuthToken.isNullOrBlank()) {
                            chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer $readNewAuthToken")
                                .build()
                        } else {
                            chain.request()
                        }
                        return@addInterceptor chain.proceed(newRequestWithNewToken)
                    } else {
                        runBlocking { clearDataStoreUseCase.invoke() }
                    }


                }
            }
            return@addInterceptor response
        }
        return client.build()
    }

    @Provides
    @Singleton
    @Named("OkHttpClientWithBasicAuth")
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
    @Named("retrofitWithBasicAuthOkHttpClient")
    fun provideRetrofitClientForLogIn(
        @Named("OkHttpClientWithBasicAuth") okHttpClient: OkHttpClient,
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
    @Named("RetrofitForMap")
    fun provideRetrofitClientForMap(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideLatLngService(@Named("RetrofitForMap") retrofit: Retrofit): LatLngService {
        return retrofit.create(LatLngService::class.java)
    }

    @Singleton
    @Provides
    fun provideLogInService(@Named("retrofitWithBasicAuthOkHttpClient") retrofit: Retrofit): LogInService {

        return retrofit.create(LogInService::class.java)
    }


    @Singleton
    @Provides
    fun provideHandleResponse(): HandleResponse {
        return HandleResponse()
    }

    @Singleton
    @Provides
    fun provideSignUpService(retrofit: Retrofit): SignUpService {
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

    @Singleton
    @Provides
    fun provideRefreshTokenService(@Named("retrofitWithBasicAuthOkHttpClient") retrofit: Retrofit): RefreshTokenService {
        return retrofit.create(RefreshTokenService::class.java)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }


}