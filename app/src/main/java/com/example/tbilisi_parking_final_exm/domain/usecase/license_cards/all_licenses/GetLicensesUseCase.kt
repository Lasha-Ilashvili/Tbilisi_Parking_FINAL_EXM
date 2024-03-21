package com.example.tbilisi_parking_final_exm.domain.usecase.license_cards.all_licenses

import com.example.tbilisi_parking_final_exm.domain.repository.license_cards.all_licenses.LicensesRepository
import javax.inject.Inject

class GetLicensesUseCase @Inject constructor(
    private val licensesRepository: LicensesRepository
) {

    suspend operator fun invoke() =
        licensesRepository.getLicenses()
}