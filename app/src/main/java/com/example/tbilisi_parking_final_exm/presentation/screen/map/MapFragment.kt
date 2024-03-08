package com.example.tbilisi_parking_final_exm.presentation.screen.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
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
import com.example.tbilisi_parking_final_exm.presentation.state.map.MapState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
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
            addMarkers(it)
        }

        userLatLng?.let {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 19f))

            userLocationMarker?.remove()

            userLocationMarker = map.addMarker(MarkerOptions().position(it))
        }
    }

    private fun addMarkers(location: MarkerLocation) {
        map.addMarker(
            MarkerOptions()
                .title(location.lotNumber)
                .position(location.latLng)
                .icon(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_parking
                    ).toBitmapDescriptor()
                )
        )
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

    private fun Drawable?.toBitmapDescriptor(): BitmapDescriptor {
        if (this == null) {
            return BitmapDescriptorFactory.defaultMarker()
        }

        return BitmapDescriptorFactory.fromBitmap(toBitmap())
    }
}