package com.example.tbilisi_parking_final_exm.presentation.screen.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.tbilisi_parking_final_exm.R
import com.example.tbilisi_parking_final_exm.databinding.FragmentMapBinding
import com.example.tbilisi_parking_final_exm.presentation.base.BaseFragment
import com.example.tbilisi_parking_final_exm.presentation.extension.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {

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

    private fun setupMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            map = googleMap

            map.setOnMapClickListener {
                userLocationMarker?.remove()
            }
        }
    }

    private fun setupLocationPermissionLauncher() {
        locationPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                if (permissions.containsValue(true)) {
                    showCurrentLocation()
                } else {
                    binding.root.showToast(getString(R.string.permissions_denied))
                }
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
                    val userLatLng = LatLng(location.latitude, location.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 19f))

                    userLocationMarker?.remove()

                    userLocationMarker = map.addMarker(MarkerOptions().position(userLatLng))
                }
            }
        } else {
            binding.root.showToast(getString(R.string.permissions_denied))
        }
    }

    override fun bindObserves() {
    }


    //    private lateinit var mMap: GoogleMap
//
//    override fun bind() {
//        // Initialize the map fragment
//
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//    }
//
//    override fun bindViewActionListeners() {
//
//    }
//
//    override fun bindObserves() {
//
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        loadAddressesAndMarkers()
//    }
//
//    private fun loadAddressesAndMarkers() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            val addresses = readAndParseAddresses()
//            placeMarkers(addresses)
//        }
//    }
//
//    private fun readAndParseAddresses(): List<String> {
//        val jsonString = readJsonFile(R.raw.addresses)
//        val jsonArray = JSONArray(jsonString)
//        val addresses = mutableListOf<String>()
//        for (i in 0 until jsonArray.length()) {
//            addresses.add(jsonArray.getJSONObject(i).getString("data"))
//        }
//        return addresses
//    }
//
//    private fun readJsonFile(resourceId: Int): String {
//        val inputStream = resources.openRawResource(resourceId)
//        val reader = BufferedReader(InputStreamReader(inputStream))
//        return reader.readText()
//    }
//
//    private fun placeMarkers(addresses: List<String>) {
//        for (address in addresses) {
//            // Use a geocoding service to get the LatLng for the address
//            // For simplicity, this example assumes you have a function `getLatLngFromAddress`
//            val latLng = getLatLngFromAddress(address)!!
//            mMap.addMarker(MarkerOptions().position(latLng).title(address))
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
//        }
//    }
//
//
//    private fun getLatLngFromAddress(address: String): LatLng? {
//        val coder = Geocoder(requireContext())
//
//        var res: LatLng? = null
//
//        try {
//            coder.getFromLocationName(address, 1)?.let {
//                val location = it[0]
//                res = LatLng(location.latitude, location.longitude)
//            }
//        } catch (e: Exception) {
//            return res
//        }
//        return res
//    }

    //    override fun bind() {
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//
//        mapFragment.getMapAsync { googleMap ->
//            addMarkers(googleMap)
//        }
//    }
//
//    private fun addMarkers(googleMap: GoogleMap) {
//
//    }
//
//    override fun bindViewActionListeners() {
//
//    }
//
//    override fun bindObserves() {
//
//    }
}