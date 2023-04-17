package com.dicoding.gitu.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val photo: String, //ini adl url utk foto
    val username: String, //ini adl nama
    val followers: String, //ini adl url utk followers
    val following: String //ini adl url utk following
): Parcelable
