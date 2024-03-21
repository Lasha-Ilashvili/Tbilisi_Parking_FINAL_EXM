package com.example.tbilisi_parking_final_exm.presentation.state.parking.active_licenses

import com.example.tbilisi_parking_final_exm.presentation.model.active_licenses.ActiveLicense


data class ActiveLicensesState(
    val isLoading: Boolean = false,
    val data: List<ActiveLicense>? = null,
    val errorMessage: String? = null
)