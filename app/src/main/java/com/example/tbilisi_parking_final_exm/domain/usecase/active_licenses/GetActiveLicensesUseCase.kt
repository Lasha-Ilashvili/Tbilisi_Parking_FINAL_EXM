package com.example.tbilisi_parking_final_exm.domain.usecase.active_licenses

import com.example.tbilisi_parking_final_exm.domain.repository.parking.active_licenses.ActiveLicensesRepository
import javax.inject.Inject

class GetActiveLicensesUseCase @Inject constructor(
    private val activeLicensesRepository: ActiveLicensesRepository
) {

    suspend operator fun invoke(carId: Int) =
        activeLicensesRepository.getActiveLicenses(carId)
}