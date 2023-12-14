package com.example.worldwise.presentation.screens.map.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class GooglePlaceModel (
    @SerializedName("business_status")
    @Expose
    val businessStatus: String? = null,

    @SerializedName("geometry")
    @Expose
    val geometry: GeometryModel? = null,

    @SerializedName("icon")
    @Expose
    val icon: String? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("obfuscated_type")
    @Expose
    val obfuscatedType: List<Any>? = null,

    @SerializedName("photos")
    @Expose
    val photos: List<PhotoModel>? = null,

    @SerializedName("place_id")
    @Expose
    val placeId: String? = null,


    @SerializedName("rating")
    @Expose
    val rating: Double? = null,

    @SerializedName("reference")
    @Expose
    val reference: String? = null,

    @SerializedName("scope")
    @Expose
    val scope: String? = null,

    @SerializedName("types")
    @Expose
    val types: List<String>? = null,

    @SerializedName("user_ratings_total")
    @Expose
    val userRatingsTotal: Int? = null,

    @SerializedName("vicinity")
    @Expose
    val vicinity: String? = null,

    @Expose(serialize = false, deserialize = false)
    val isSaved: Boolean,
)