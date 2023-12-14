package com.example.worldwise.presentation.screens.home.models

import com.example.worldwise.utils.DefaultDiffUtilItemCallback
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsModel(
    @SerializedName("Title")
    val Title: String = "",
    @SerializedName("Author")
    val Author: String = "",
    @SerializedName("Theme")
    val Theme: String = "",
    @SerializedName("Chapter")
    val Chapter: String = "",
    @SerializedName("Text")
    val Text: String = "",
    @SerializedName("Category")
    val Category: String = "",
): Serializable {

    companion object {
        val diffUtil = DefaultDiffUtilItemCallback<NewsModel> { oldItem, newItem ->
            oldItem.Title == newItem.Title
        }
    }
}