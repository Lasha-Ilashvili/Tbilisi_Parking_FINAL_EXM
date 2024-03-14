package com.example.tbilisi_parking_final_exm.domain.user_data_key

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val accessToken = stringPreferencesKey("access_token")
    val refreshToken = stringPreferencesKey("refresh_token")
    val userId = stringPreferencesKey("userId")
}