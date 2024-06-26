package com.example.tbilisi_parking_final_exm.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.data_source.map.LatLngDataSource
import com.example.tbilisi_parking_final_exm.data.repository.datastore.DataStoreRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.license_cards.all_license.LicensesRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.license_cards.buy_license.BuyLicenseRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.log_in.LogInRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.map.MarkerLocationsRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.parking.active_licenses.ActiveLicensesRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.parking.active_parking.GetActiveParkingRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.parking.add_vehicle.AddVehicleRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.parking.delete_vehicle.DeleteVehicleRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.parking.edit_vehicle.EditVehicleRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.parking.finish_parking.FinishParkingRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.parking.get_vehicles.GetAllVehicleRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.parking.start_parking.StartParkingRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.refresh_token.RefreshTokenRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.sign_up.SignUpRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.transactions.TransactionsRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.user_panel.profile.ProfileRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.balance.AddToBalanceRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.balance.GetBalanceRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.cards.DeleteCardRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.repository.user_panel.wallet.cards.GetUserCardsRepositoryImpl
import com.example.tbilisi_parking_final_exm.data.service.license_cards.all_licenses.LicensesService
import com.example.tbilisi_parking_final_exm.data.service.license_cards.buy_license.BuyLicenseService
import com.example.tbilisi_parking_final_exm.data.service.log_in.LogInService
import com.example.tbilisi_parking_final_exm.data.service.map.LatLngService
import com.example.tbilisi_parking_final_exm.data.service.parking.active_licenses.ActiveLicensesService
import com.example.tbilisi_parking_final_exm.data.service.parking.active_parking.GetActiveParkingService
import com.example.tbilisi_parking_final_exm.data.service.parking.add_vehicle.AddVehicleService
import com.example.tbilisi_parking_final_exm.data.service.parking.delete_vehicle.DeleteVehicleService
import com.example.tbilisi_parking_final_exm.data.service.parking.edit_vehicle.EditVehicleService
import com.example.tbilisi_parking_final_exm.data.service.parking.finish_parking.FinishParkingService
import com.example.tbilisi_parking_final_exm.data.service.parking.get_vehicle.GetAllVehicleService
import com.example.tbilisi_parking_final_exm.data.service.parking.start_parking.StartParkingService
import com.example.tbilisi_parking_final_exm.data.service.refresh_token.RefreshTokenService
import com.example.tbilisi_parking_final_exm.data.service.sign_up.SignUpService
import com.example.tbilisi_parking_final_exm.data.service.transactions.TransactionsService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.profile.ProfileService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance.AddToBalanceService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.balance.GetBalanceService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards.DeleteUserCardService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards.GetUserCardsService
import com.example.tbilisi_parking_final_exm.data.service.user_panel.wallet.cards.SaveCardService
import com.example.tbilisi_parking_final_exm.domain.repository.datastore.DataStoreRepository
import com.example.tbilisi_parking_final_exm.domain.repository.license_cards.all_licenses.LicensesRepository
import com.example.tbilisi_parking_final_exm.domain.repository.license_cards.buy_license.BuyLicenseRepository
import com.example.tbilisi_parking_final_exm.domain.repository.log_in.LogInRepository
import com.example.tbilisi_parking_final_exm.domain.repository.map.MarkerLocationsRepository
import com.example.tbilisi_parking_final_exm.domain.repository.parking.active_licenses.ActiveLicensesRepository
import com.example.tbilisi_parking_final_exm.domain.repository.parking.active_parking.GetActiveParkingRepository
import com.example.tbilisi_parking_final_exm.domain.repository.parking.add_vehicle.AddVehicleRepository
import com.example.tbilisi_parking_final_exm.domain.repository.parking.delete_vehicle.DeleteVehicleRepository
import com.example.tbilisi_parking_final_exm.domain.repository.parking.edit_vehicle.EditVehicleRepository
import com.example.tbilisi_parking_final_exm.domain.repository.parking.finish_parking.FinishParkingRepository
import com.example.tbilisi_parking_final_exm.domain.repository.parking.get_vehicles.GetAllVehicleRepository
import com.example.tbilisi_parking_final_exm.domain.repository.parking.start_parking.StartParkingRepository
import com.example.tbilisi_parking_final_exm.domain.repository.refresh_token.RefreshTokenRepository
import com.example.tbilisi_parking_final_exm.domain.repository.sign_up.SignUpRepository
import com.example.tbilisi_parking_final_exm.domain.repository.transactions.TransactionsRepository
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.profile.ProfileRepository
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.balance.AddToBalanceRepository
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.cards.DeleteCardRepository
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.cards.GetBalanceRepository
import com.example.tbilisi_parking_final_exm.domain.repository.user_panel.wallet.cards.GetUserCardsRepository
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
    fun provideRefreshTokenRepository(
        handleResponse: HandleResponse,
        refreshTokenService: RefreshTokenService
    ): RefreshTokenRepository {
        return RefreshTokenRepositoryImpl(
            handleResponse = handleResponse,
            refreshTokenService = refreshTokenService
        )
    }


    @Singleton
    @Provides
    fun provideAddToBalanceRepository(
        handleResponse: HandleResponse,
        addToBalanceService: AddToBalanceService,
        deleteUserCardService: DeleteUserCardService,
        saveCardService: SaveCardService
    ): AddToBalanceRepository {
        return AddToBalanceRepositoryImpl(
            handleResponse = handleResponse,
            addToBalanceService = addToBalanceService,
            deleteUserCardService = deleteUserCardService,
            saveCardService = saveCardService
        )
    }

    @Singleton
    @Provides
    fun provideDeleteCardRepository(
        handleResponse: HandleResponse,
        deleteUserCardService: DeleteUserCardService
    ): DeleteCardRepository {
        return DeleteCardRepositoryImpl(
            handleResponse = handleResponse,
            deleteUserCardService = deleteUserCardService
        )
    }

    @Singleton
    @Provides
    fun provideGetUserCardsRepository(
        handleResponse: HandleResponse,
        getUserCardsService: GetUserCardsService
    ): GetUserCardsRepository {
        return GetUserCardsRepositoryImpl(
            handleResponse = handleResponse,
            getUserCardsService = getUserCardsService
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

    @Singleton
    @Provides
    fun provideLicensesRepository(
        handleResponse: HandleResponse,
        licensesService: LicensesService
    ): LicensesRepository {
        return LicensesRepositoryImpl(
            handleResponse = handleResponse,
            licensesService = licensesService
        )
    }

    @Singleton
    @Provides
    fun provideStartParkingRepository(
        handleResponse: HandleResponse,
        startParkingService: StartParkingService
    ): StartParkingRepository {
        return StartParkingRepositoryImpl(
            handleResponse = handleResponse,
            startParkingService = startParkingService
        )
    }

    @Singleton
    @Provides
    fun provideFinishParkingRepository(
        handleResponse: HandleResponse,
        finishParkingService: FinishParkingService
    ): FinishParkingRepository {
        return FinishParkingRepositoryImpl(
            handleResponse = handleResponse,
            finishParkingService = finishParkingService
        )
    }

    @Singleton
    @Provides
    fun provideGetActiveParkingRepository(
        handleResponse: HandleResponse,
        getActiveParkingService: GetActiveParkingService
    ): GetActiveParkingRepository {
        return GetActiveParkingRepositoryImpl(
            handleResponse = handleResponse,
            getActiveParkingService = getActiveParkingService
        )
    }

    @Singleton
    @Provides
    fun provideDeleteVehicleRepository(
        handleResponse: HandleResponse,
        deleteVehicleService: DeleteVehicleService
    ): DeleteVehicleRepository {
        return DeleteVehicleRepositoryImpl(
            handleResponse = handleResponse,
            deleteVehicleService = deleteVehicleService
        )
    }

    @Singleton
    @Provides
    fun provideBuyLicenseRepository(
        handleResponse: HandleResponse,
        buyLicenseService: BuyLicenseService,
        deleteUserCardService: DeleteUserCardService,
        saveCardService: SaveCardService
    ): BuyLicenseRepository {
        return BuyLicenseRepositoryImpl(
            handleResponse = handleResponse,
            buyLicenseService = buyLicenseService,
            deleteUserCardService = deleteUserCardService,
            saveCardService = saveCardService
        )
    }

    @Singleton
    @Provides
    fun provideActiveLicensesRepository(
        handleResponse: HandleResponse,
        activeLicensesService: ActiveLicensesService
    ): ActiveLicensesRepository {
        return ActiveLicensesRepositoryImpl(
            handleResponse = handleResponse,
            activeLicensesService = activeLicensesService
        )
    }

    @Singleton
    @Provides
    fun provideTransactionsRepository(
        handleResponse: HandleResponse,
        transactionsService: TransactionsService
    ): TransactionsRepository {
        return TransactionsRepositoryImpl(
            handleResponse = handleResponse,
            transactionsService = transactionsService
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