package com.example.tbilisi_parking_final_exm.domain.usecase.license_cards.buy_license

import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.model.license_cards.buy_license.GetBuyLicenseRequest
import com.example.tbilisi_parking_final_exm.domain.model.user_panel.wallet.cards.GetCardDetails
import com.example.tbilisi_parking_final_exm.domain.model.vehicle.vehicle.GetVehicle
import com.example.tbilisi_parking_final_exm.domain.repository.license_cards.buy_license.BuyLicenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class BuyLicenseUseCase @Inject constructor(
    private val buyLicenseRepository: BuyLicenseRepository
) {

    suspend operator fun invoke(
        vehicles: List<GetVehicle>,
        plateNumber: String,
        descriptionId: Int,
        userId: Int,
        getCardDetails: GetCardDetails?
    ): Flow<Resource<Unit>> {
        val vehicleId = getVehicleId(vehicles, plateNumber)

        if (vehicleId != null) {
            return buyLicenseRepository.buyLicense(
                getBuyLicenseRequest = GetBuyLicenseRequest(
                    vehicleId = vehicleId,
                    descriptionId = descriptionId,
                    userId = userId
                ),
                getCardDetails = getCardDetails
            )
        }

        return flow {
            emit(Resource.Error("No such car!"))
        }
    }

    private fun getVehicleId(vehicles: List<GetVehicle>, plateNumber: String) =
        vehicles.find { it.plateNumber == plateNumber }?.id
}