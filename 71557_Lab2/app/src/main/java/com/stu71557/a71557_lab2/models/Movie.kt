package com.stu71557.a71557_lab2.models

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes

/**
 * The @DrawableRes annotation on the bannerResId
 * parameter Denotes that an integer parameter,
 * field or method return value is expected to be a drawable resource reference
 */
data class Movie (
    val id: Int,
    val title: String,
    @DrawableRes val posterResId: Int,
    val starring: String,
    val description: String,
    val runningTime: String,
    val maxSeats: Int = (0..15).random()
)