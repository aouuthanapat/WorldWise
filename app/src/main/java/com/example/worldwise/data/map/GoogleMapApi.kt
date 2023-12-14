package com.example.worldwise.data.map

import com.example.worldwise.data.map.contracts.GoogleResponse
import retrofit2.http.GET
import retrofit2.http.Url


interface GoogleMapApi {
    @GET
    suspend fun getNearByPlaces(@Url url: String?): GoogleResponse?
}