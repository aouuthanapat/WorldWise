package com.example.worldwise.presentation.screens.authorization.signUp.model

data class User(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val photoId: Int? = null,
    val number: String = "",
    val nickName: String = "",
)

