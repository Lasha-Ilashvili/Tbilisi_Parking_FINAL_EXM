package com.example.tbilisi_parking_final_exm.presentation.screen.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentMapBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.map.MapEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.example.tbilisi_parking_final_exm.presentation.model.map.MarkerLocation
import com.example.tbilisi_parking_final_exm.presentation.screen.map.adapter.MarkerLocationsRenderer
import com.example.tbilisi_parking_final_exm.presentation.state.map.MapState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.InputStream


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {

    private val viewModel: MapViewModel by viewModels()

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>
    private var userLocationMarker: Marker? = null

    override fun bind() {
        setupMap()
        setupLocationPermissionLauncher()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun bindViewActionListeners() {
        binding.btnCurrentLocation.setOnClickListener {
            checkAndRequestPermissions()
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mapState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* Implementation details */

    private fun setupMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->
            map = googleMap

            viewModel.onEvent(MapEvent.SetMarkers(jsonToString(R.raw.addresses)))

            map.setOnMapClickListener {
                userLocationMarker?.remove()
            }
        }
    }

    private fun handleState(mapState: MapState) = with(mapState) {
        markerLocation?.let {

            map.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()
                it.forEach { location ->
                    bounds.include(location.latLng)
                }
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 200))
            }

            addClusteredMarkers(it)
        }

        userLatLng?.let {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 19f))

            userLocationMarker?.remove()

            userLocationMarker = map.addMarker(MarkerOptions().position(it))
        }
    }

    private fun addClusteredMarkers(markerLocations: List<MarkerLocation>) {
        val clusterManager = ClusterManager<MarkerLocation>(requireContext(), map)

        clusterManager.renderer =
            MarkerLocationsRenderer(
                requireContext(),
                map,
                clusterManager
            )

        clusterManager.clearItems()
        clusterManager.addItems(markerLocations)
        clusterManager.cluster()

        map.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }

//        val clusterManager = ClusterManager<MarkerLocation>(requireContext(), map)
//
//        clusterManager.renderer = DefaultClusterRenderer(requireContext(), map, clusterManager)
//
//        clusterManager.clearItems()
//
//        clusterManager.addItems(markerLocations)
//
//        clusterManager.cluster()
//
//        map.setOnCameraIdleListener {
//            clusterManager.onCameraIdle()
//        }
    }

    private fun setupLocationPermissionLauncher() {
        locationPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

                if (permissions.containsValue(true))
                    showCurrentLocation()
                else
                    binding.root.showToast(getString(R.string.permissions_denied))

            }
    }

    private fun checkAndRequestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        locationPermissionLauncher.launch(permissions)
    }

    private fun showCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                setUserLocation(location)
            }
        } else {
            binding.root.showToast(getString(R.string.permissions_denied))
        }
    }

    private fun setUserLocation(location: Location) {
        viewModel.onEvent(
            MapEvent.SetUserLocation(
                LatLng(
                    location.latitude,
                    location.longitude
                )
            )
        )
    }

    private fun jsonToString(addresses: Int): String {
        val jsonFile: InputStream = requireContext().resources.openRawResource(addresses)
        return jsonFile.bufferedReader().use { it.readText() }
    }
}