package com.example.worldwise.presentation.common

import com.example.worldwise.R

fun getProfilePhoto(photo: Int?) = when(photo) {
    ProfilePhoto.SNAKE.photoId -> R.drawable.snake
    ProfilePhoto.ATHLETE.photoId -> R.drawable.athlete
    ProfilePhoto.GOTHBOY.photoId -> R.drawable.gothboy
    ProfilePhoto.ANDREADEL.photoId -> R.drawable.andreadel
    ProfilePhoto.JON.photoId -> R.drawable.jon
    ProfilePhoto.JESSICA.photoId -> R.drawable.jessica
    else -> R.drawable.photo_not_aviable
}