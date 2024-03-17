package com.example.tbilisi_parking_final_exm.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.data_source.map.LatLngDataSource
import com.example.tbilisi_parking_final_exm.data.data_source.user_panel.wallet.balance.RememberCardDataSource
import com.example.tbilisi_parking_final_exm.data.repository.add_vehicle.AddVehicleRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.datastore.DataStoreRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.edit_vehicle.EditVehicleRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.get_vehicles.GetAllVehicleRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.log_in.LogInRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.map.MarkerLocationsRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.sign_up.SignUpRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.user_panel.profile.ProfileRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.balance.AddToBalanceRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.main.GetBalanceRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.main.RememberedCardsRepositoryImpl
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
import com.example.tbilisi_parking_final_exm.domain.repository.add_vehicle.AddVehicleRepository
import com.example.tbilisi_parking_final_exm.domain.repository.datastore.DataStoreRepository
import com.example.tbilisi_parking_final_exm.domain.repository.edit_vehicle.EditVehicleRepository
import com.example.tbilisi_parking_final_exm.domain.repository.get_vehicles.GetAllVehicleRepository
import com.example.tbilisi_parking_final_exm.domain.repository.log_in.LogInRepository
import com.example.tbilisi_parking_final_exm.domain.repository.map.MarkerLocationsRepository
import com.example.tbilisi_parking_final_exm.domain.repository.sign_up.SignUpRepository
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.profile.ProfileRepository
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.balance.AddToBalanceRepository
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.main.GetBalanceRepository
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.main.RememberedCardsRepository
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

    @Singleton
    @Provides
    fun provideAddVehicleRepository(
        handleResponse: HandleResponse,
        addVehicleService: AddVehicleService
    ): AddVehicleRepository {
        return AddVehicleRepositoryImpl(
            handleResponse = handleResponse,
            addVehicleService = addVehicleService
        )
    }

    @Singleton
    @Provides
    fun provideGetAllVehiclesRepository(
        handleResponse: HandleResponse,
        getAllVehicleService: GetAllVehicleService
    ): GetAllVehicleRepository {
        return GetAllVehicleRepositoryImpl(
            handleResponse = handleResponse,
            getAllVehicleService = getAllVehicleService
        )
    }

    @Singleton
    @Provides
    fun provideEditVehicleRepository(
        handleResponse: HandleResponse,
        editVehicleService: EditVehicleService
    ): EditVehicleRepository {
        return EditVehicleRepositoryImpl(
            handleResponse = handleResponse,
            editVehicleService = editVehicleService
        )
    }

    @Singleton
    @Provides
    fun provideAddToBalanceRepository(
        handleResponse: HandleResponse,
        addToBalanceService: AddToBalanceService,
        deleteRememberedCardService: DeleteRememberedCardService,
        rememberCardService: RememberCardService
    ): AddToBalanceRepository {
        return AddToBalanceRepositoryImpl(
            handleResponse = handleResponse,
            addToBalanceService = addToBalanceService,
            deleteRememberedCardService = deleteRememberedCardService,
            rememberCardService = rememberCardService
//            rememberCardDataSource = rememberCardDataSource
        )
    }

    @Singleton
    @Provides
    fun provideRememberCardDataSource(
        handleResponse: HandleResponse,
        rememberCardService: RememberCardService
    ): RememberCardDataSource {
        return RememberCardDataSource(
            handleResponse = handleResponse,
            rememberCardService = rememberCardService
        )
    }

    @Singleton
    @Provides
    fun provideRememberedCardsRepository(
        handleResponse: HandleResponse,
        rememberedCardsService: RememberedCardsService,
        deleteRememberedCardService: DeleteRememberedCardService
    ): RememberedCardsRepository {
        return RememberedCardsRepositoryImpl(
            handleResponse = handleResponse,
            rememberedCardsService = rememberedCardsService,
            deleteRememberedCardService = deleteRememberedCardService
        )
    }

    @Singleton
    @Provides
    fun provideGetBalanceRepository(
        handleResponse: HandleResponse,
        getBalanceService: GetBalanceService
    ): GetBalanceRepository {
        return GetBalanceRepositoryImpl(
            handleResponse = handleResponse,
            getBalanceService = getBalanceService
        )
    }
}