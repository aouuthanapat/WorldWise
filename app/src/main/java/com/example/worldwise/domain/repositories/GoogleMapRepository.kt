package com.example.worldwise.domain.repositories

import com.example.worldwise.data.map.GoogleMapApi

class GoogleMapRepository(private val api: GoogleMapApi) {

    suspend fun getPlace(url: String) = api.getNearByPlaces(url)
}