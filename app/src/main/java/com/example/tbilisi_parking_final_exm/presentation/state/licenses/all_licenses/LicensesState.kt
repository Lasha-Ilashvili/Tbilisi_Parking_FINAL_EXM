package com.example.tbilisi_parking_final_exm.presentation.state.licenses.all_licenses

import com.example.tbilisi_parking_final_exm.presentation.model.Licenses.License


data class LicensesState(
    val isLoading: Boolean = false,
    val data: List<License>? = null,
    val errorMessage: String? = null
)