package com.example.worldwise.presentation.screens.map.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class LocationModel (

    @SerializedName("lat")
    @Expose
    val lat: Double? = null,

    @SerializedName("lng")
    @Expose
    var lng: Double? = null
)