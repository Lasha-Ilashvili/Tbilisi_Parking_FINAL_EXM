package com.example.tbilisi_parking_final_exm.presentation.state.license_cards.all_licenses

import com.example.tbilisi_parking_final_exm.presentation.model.license_cards.all_licenses.License


data class LicensesState(
    val isLoading: Boolean = false,
    val data: List<License>? = null,
    val errorMessage: String? = null,
    val sessionCompleted: Boolean = false
)