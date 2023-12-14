package com.example.worldwise.presentation.screens.map.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class PhotoModel (
    @SerializedName("height")
    @Expose
    private val height: Int? = null,

    @SerializedName("html_attributions")
    @Expose
    private val htmlAttributions: List<String>? = null,

    @SerializedName("photo_reference")
    @Expose
    private val photoReference: String? = null,

    @SerializedName("width")
    @Expose
    private val width: Int? = null
)