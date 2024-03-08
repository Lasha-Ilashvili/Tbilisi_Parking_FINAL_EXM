package com.example.tbilisi_parking_final_exm.presentation.screen.map.adapter

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.presentation.extension.toBitmapDescriptor
import com.example.tbilisi_parking_final_exm.presentation.model.map.MarkerLocation
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class MarkerLocationsRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<MarkerLocation>
) : DefaultClusterRenderer<MarkerLocation>(context, map, clusterManager) {


    private val parkingIcon: BitmapDescriptor by lazy {
        AppCompatResources.getDrawable(
            context,
            R.drawable.ic_parking
        ).toBitmapDescriptor()
    }

    override fun onBeforeClusterItemRendered(item: MarkerLocation, markerOptions: MarkerOptions) {
        markerOptions
            .title(item.lotNumber)
            .position(item.latLng)
            .icon(parkingIcon)
    }

    override fun onClusterItemRendered(clusterItem: MarkerLocation, marker: Marker) {
        marker.tag = clusterItem
    }
}