package com.example.skillcinema.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomSelection(
    val name: String,
    val country: Int,
    val genre: Int
) : Parcelable
