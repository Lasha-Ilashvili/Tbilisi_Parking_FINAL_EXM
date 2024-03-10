package com.example.tbilisi_parking_final_exm.presentation.screen.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.provider.Settings
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentMapBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.event.map.MapEvent
import com.example.tbilisi_parking_final_exm.presentation.extension.jsonToString
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
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {

    private val viewModel: MapViewModel by viewModels()

    private var map: GoogleMap? = null
    private var clusterManager: ClusterManager<MarkerLocation>? = null
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
            viewModel.onEvent(MapEvent.UpdateUserLocation(shouldShowUserLocation = true))
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
            map = googleMap.apply {
                checkAndRequestPermissions()

                setTheme(this)

                viewModel.onEvent(MapEvent.SetMarkers(R.raw.addresses.jsonToString(requireContext())))

                setOnMapClickListener { _ ->
                    userLocationMarker?.remove()
                    viewModel.onEvent(MapEvent.UpdateUserLocation(shouldShowUserLocation = false))
                }
            }
        }
    }

    private fun setTheme(googleMap: GoogleMap) {
        if (isDarkMode(requireContext())) {
            googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    R.raw.map_style_dark
                )
            )
        }
    }

    private fun addClusteredMarkers(markerLocations: List<MarkerLocation>) {
        map?.let { googleMap ->

            if (clusterManager == null) {
                initializeClusterManager(googleMap)
            }

            clusterManager?.apply {
                addItems(markerLocations)
                cluster()
            }
        }
    }

    private fun initializeClusterManager(googleMap: GoogleMap) {
        clusterManager = ClusterManager<MarkerLocation>(requireContext(), googleMap).apply {
            renderer = MarkerLocationsRenderer(requireContext(), googleMap, this)
            googleMap.setOnCameraIdleListener {
                onCameraIdle()
            }
        }
    }

    private fun handleState(mapState: MapState) = with(mapState) {
        binding.loadingProgressBar.visibility = if (isLoading) VISIBLE else GONE

        errorMessage?.let {
            binding.root.showToast(errorMessage)
            viewModel.onEvent(MapEvent.ResetErrorMessage)
        }

        markerLocation?.let { markerLocations ->
            addClusteredMarkers(markerLocations)
        }

        userLatLng?.let { userLatLng ->
            userLocationMarker?.remove()

            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 19f))

            userLocationMarker = map?.addMarker(MarkerOptions().position(userLatLng))
        }
    }

    private fun setupLocationPermissionLauncher() {
        locationPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

                if (permissions.containsValue(true) && viewModel.mapState.value.shouldShowUserLocation)
                    showCurrentLocation()
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
                location?.let {
                    setUserLocation(it)
                } ?: showEnableLocationDialog()
            }
        } else {
            binding.root.showToast(getString(R.string.permissions_denied))
        }
    }

    private fun showEnableLocationDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        setUpDialog(dialogBuilder)
        dialogBuilder.create().show()
    }

    private fun setUpDialog(dialogBuilder: AlertDialog.Builder) = with(dialogBuilder) {
        setTitle(getString(R.string.location_services_disabled))
        setMessage(getString(R.string.asking_for_location))

        setPositiveButton(getString(R.string.enable)) { _, _ ->
            val locationSettingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(locationSettingsIntent)
        }

        setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
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

    private fun isDarkMode(context: Context): Boolean {
        return (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }
}