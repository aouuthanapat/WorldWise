package com.example.worldwise.data.map.contracts

import com.example.worldwise.presentation.screens.map.models.GooglePlaceModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GoogleResponse(
    @SerializedName("results")
    @Expose
    val googlePlaceModelList: List<GooglePlaceModel>,

    @SerializedName("error_message")
    @Expose
    val error: String?
)