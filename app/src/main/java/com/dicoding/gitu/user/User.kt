package com.dicoding.gitu.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val photo: String,
    val username: String,
    val followers: String,
    val following: String
): Parcelable
