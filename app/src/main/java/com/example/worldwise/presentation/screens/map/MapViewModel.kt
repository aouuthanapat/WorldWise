package com.example.worldwise.presentation.screens.map

import android.app.Application
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldwise.R
import com.example.worldwise.data.map.contracts.GoogleResponse
import com.example.worldwise.domain.repositories.GoogleMapRepository
import com.example.worldwise.presentation.screens.map.models.PlaceModel
import com.example.worldwise.utils.noReply
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MapViewModel(
    private val application: Application,
    private val googleMapRepository: GoogleMapRepository
) : ViewModel() {

    private val _places: MutableSharedFlow<GoogleResponse> = noReply()
    val places: Flow<GoogleResponse> = _places

    val placesName: ArrayList<PlaceModel> = buildPlaceList()


    fun getPlaces(placeName: String, isLocationPermissionOk: Boolean, currentLocation: Location) {
        if (isLocationPermissionOk) {
            val url = ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                    + currentLocation.latitude + "," + currentLocation.longitude
                    + "&radius=" + RADIUS + "&type=" + placeName + "&key=" +
                    "AIzaSyBO6DgQTUXHH5YnuqxDNra2r9ri9I4q7Rg")


            viewModelScope.launch {
                googleMapRepository.getPlace(url)?.let {
                    _places.emit(it)
                }
            }
        }
    }

    private fun buildPlaceList() = arrayListOf(
        PlaceModel(1, "Restaurant", "restaurant"),
        PlaceModel(2, "ATM", "atm"),
        PlaceModel(3, "Gas", "gas_station"),
        PlaceModel(4, "Groceries", "supermarket"),
        PlaceModel(5, "Hotels", "hotel"),
        PlaceModel(6, "Pharmacies", "pharmacy"),
        PlaceModel(7, "Hospitals & Clinics", "hospital"),
        PlaceModel(8, "Car Wash", "car_wash"),
        PlaceModel(9, "Beauty Salons", "beauty_salon")
    )


    companion object {
        private const val RADIUS = 500
    }
}