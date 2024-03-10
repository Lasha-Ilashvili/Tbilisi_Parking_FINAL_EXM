package com.example.tbilisi_parking_final_exm.presentation.model.map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem


data class MarkerLocation(
    val lotNumber: String,
    val address: String,
    val latLng: LatLng
) : ClusterItem {

    override fun getPosition(): LatLng = latLng

    override fun getTitle(): String = lotNumber

    override fun getSnippet(): String = address
}
