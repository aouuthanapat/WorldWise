package com.example.worldwise.presentation.screens.map

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.text.Layout.Alignment
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.FragmentMapBinding
import com.example.worldwise.presentation.screens.map.models.GooglePlaceModel
import com.example.worldwise.presentation.screens.map.models.PlaceModel
import com.example.worldwise.utils.AppPermissions
import com.example.worldwise.utils.LOCATION_REQUEST_CODE
import com.example.worldwise.utils.bind
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback, OnMarkerClickListener {
    private val binding by viewBinding(FragmentMapBinding::bind)
    private val viewModel: MapViewModel by viewModel()
    private var mGoogleMap: GoogleMap? = null
    private var appPermissions: AppPermissions? = null
    private var isLocationPermissionOk = false
    private var isTrafficEnable = false
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var currentLocation: Location? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var currentMarker: Marker? = null
    private var googlePlaceModelList: MutableList<GooglePlaceModel>? = null
    private var selectedPlaceModel: PlaceModel? = null
    private var userSavedLocationId: ArrayList<String?>? = null
    private var locationReference: DatabaseReference? = null
    private var userLocationReference: DatabaseReference? = null

    val launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        val allPermissionsGranted = !it.containsValue(false)
        if (allPermissionsGranted) {
            isLocationPermissionOk = true
            setUpGoogleMap()
        } else {
            isLocationPermissionOk = false
            Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appPermissions = AppPermissions()
        firebaseAuth = FirebaseAuth.getInstance()
        googlePlaceModelList = ArrayList()
        userSavedLocationId = ArrayList()
        locationReference = FirebaseDatabase.getInstance().getReference("Places")
        userLocationReference = FirebaseDatabase.getInstance().getReference("Users")
            .child(firebaseAuth!!.uid!!).child("Saved Locations")

        val mapFragment = (childFragmentManager
            .findFragmentById(R.id.homeMap) as SupportMapFragment?)!!

        mapFragment.getMapAsync(this)
        bindViewModelInputs()
        bindViewModelOutputs()
        for (placeModel in viewModel.placesName) {
            val chip = Chip(requireContext())
            chip.text = placeModel.name
            chip.id = placeModel.id
            chip.setPadding(8, 8, 8, 8)
            chip.setTextColor(resources.getColor(R.color.colorSecondary, null))
            chip.chipBackgroundColor = resources.getColorStateList(R.color.colorTertiary, null)
            chip.isCheckable = true
            chip.textAlignment = View.TEXT_ALIGNMENT_CENTER
            chip.isCheckedIconVisible = false
            binding.placesGroup.addView(chip)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        mGoogleMap?.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.dark_map_bg)
        )
        if (checkNotNull(appPermissions?.isLocationOk(requireContext()))) {
            isLocationPermissionOk = true
            setUpGoogleMap()
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Location Permission")
                .setMessage("Near me required location permission to show you near by places")
                .setPositiveButton("Ok") { dialog, which -> requestLocation() }
                .create().show()
        } else {
            requestLocation()
        }
    }

    private fun bindViewModelInputs() {

        binding.enableTraffic.setOnClickListener { view ->
            if (isTrafficEnable) {
                if (mGoogleMap != null) {
                    mGoogleMap!!.isTrafficEnabled = false
                    isTrafficEnable = false
                }
            } else {
                if (mGoogleMap != null) {
                    mGoogleMap!!.isTrafficEnabled = true
                    isTrafficEnable = true
                }
            }
        }
        binding.currentLocation.setOnClickListener { getCurrentLocation() }
        binding.placesGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                val placeModel: PlaceModel = viewModel.placesName[checkedId - 1]
                binding.edtPlaceName.setText(placeModel.name)
                selectedPlaceModel = placeModel
                binding.placesGroup.getChildAt(checkedId).setBackgroundColor(requireContext().getColor(R.color.colorSecondary))
                viewModel.getPlaces(placeModel.placeType, isLocationPermissionOk, checkNotNull(currentLocation))
            }
        }
    }

    private fun bindViewModelOutputs() = with(viewModel) {
        places.bind(viewLifecycleOwner) {
            if (it.googlePlaceModelList.isNotEmpty()) {
                googlePlaceModelList!!.clear()
                mGoogleMap!!.clear()
                for (i in 0 until it.googlePlaceModelList.size) {
                    addMarker(it.googlePlaceModelList[i], i)
                }
            } else if (it.error != null) {
                Snackbar.make(
                    binding.root,
                    it.error,
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                mGoogleMap!!.clear()
                googlePlaceModelList!!.clear()
            }
        }
    }

    private fun requestLocation() {
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun setUpGoogleMap() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            isLocationPermissionOk = false
            return
        }
        mGoogleMap!!.isMyLocationEnabled = true
        mGoogleMap!!.uiSettings.isTiltGesturesEnabled = true
        mGoogleMap!!.setOnMarkerClickListener { marker: Marker -> onMarkerClick(marker) }
        getCurrentLocation()
    }

    private fun setUpLocationUpdate() {
        locationRequest = LocationRequest.create()
            .setInterval(10000)
            .setFastestInterval(5000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    Log.d("TAG", "onLocationResult: " + location.longitude + " " + location.latitude)
                }
                super.onLocationResult(locationResult)
            }
        }
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            isLocationPermissionOk = false
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Location updated started", Toast.LENGTH_SHORT).show()
                }
            }
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            isLocationPermissionOk = false
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                currentLocation = location
                moveCameraToLocation(location)
            }
        }
    }


    private fun moveCameraToLocation(location: Location) {
        val cameraUpdate =
            CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 17f)
        val markerOptions = MarkerOptions()
            .position(LatLng(location.latitude, location.longitude))
            .title("Current Location")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            .snippet(firebaseAuth!!.currentUser!!.displayName)
        if (currentMarker != null) {
            currentMarker!!.remove()
        }
        currentMarker = mGoogleMap!!.addMarker(markerOptions)
        mGoogleMap!!.animateCamera(cameraUpdate)
    }

    private fun stopLocationUpdate() {
        fusedLocationProviderClient!!.removeLocationUpdates(locationCallback)
        Log.d("TAG", "stopLocationUpdate: Location Update stop")
    }

    override fun onPause() {
        super.onPause()
        if (fusedLocationProviderClient != null) stopLocationUpdate()
    }

    override fun onResume() {
        super.onResume()
        if (fusedLocationProviderClient != null) {
            startLocationUpdates()
            if (currentMarker != null) {
                currentMarker!!.remove()
            }
        }
    }


    private fun addMarker(googlePlaceModel: GooglePlaceModel, position: Int) {
        val markerOptions = MarkerOptions()
            .position(
                LatLng(
                    checkNotNull(googlePlaceModel.geometry?.location?.lat),
                    checkNotNull(googlePlaceModel.geometry?.location?.lng)
                )
            )
            .title(googlePlaceModel.name)
            .snippet(googlePlaceModel.vicinity)
        markerOptions.icon(customIcon)
        mGoogleMap!!.addMarker(markerOptions)?.setTag(position)
    }

    private val customIcon: BitmapDescriptor
        get() {
            val background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_location)
            background!!.setTint(resources.getColor(R.color.colorSecondary, null))
            background.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
            val bitmap = Bitmap.createBitmap(
                background.intrinsicWidth, background.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            background.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }

    override fun onMarkerClick(p0: Marker): Boolean {
        TODO("Not yet implemented")
    }

}