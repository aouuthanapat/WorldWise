package com.example.worldwise.presentation.screens.map.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class GeometryModel (
    @SerializedName("location")
    @Expose
    val location: LocationModel? = null
)