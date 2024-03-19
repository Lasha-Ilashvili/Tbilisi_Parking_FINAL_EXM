package com.example.tbilisi_parking_final_exm.domain.usecase.license_cards.all_licenses

import com.example.tbilisi_parking_final_exm.domain.repository.license_cards.all_licenses.LicensesRepository
import javax.inject.Inject

class GetLicensesUseCase @Inject constructor(
    private val licensesRepository: LicensesRepository
) {

    suspend operator fun invoke() =
        licensesRepository.getLicenses()


//    operator fun invoke() = flow<List<GetLicense>> {
//        listOf(
//            GetLicense(
//                title = "buy card"
//            ),
//            GetLicense(
//                title = "one day",
//                zonalCard = GetLicense.GetZonalCard(
//                    period = "1 day",
//                    price = (20),
//                    backgroundColor = "#219BCC"
//                )
//            ),
//            GetLicense(
//                title = "one week",
//                zonalCard = GetLicense.GetZonalCard(
//                    period = "1 week",
//                    price = 100,
//                    backgroundColor = "#03DAC6"
//                )
//            ),
//            GetLicense(
//                title = "one month",
//                zonalCard = GetLicense.GetZonalCard(
//                    period = "1 month",
//                    price = 300,
//                    backgroundColor = "#605A7C"
//                )
//            ),
//            GetLicense(
//                title = "six months", zonalCard = GetLicense.GetZonalCard(
//                    period = "6 months",
//                    price = 500,
//                    backgroundColor = "#E38568"
//                )
//            ),
//            GetLicense(
//                title = "one year", zonalCard = GetLicense.GetZonalCard(
//                    period = "1 year",
//                    price = 800,
//                    backgroundColor = "#68E387"
//                )
//            )
//        )
//    }
}