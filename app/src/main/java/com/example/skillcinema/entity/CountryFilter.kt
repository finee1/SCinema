package com.example.skillcinema.entity

import com.squareup.moshi.Json

interface CountryFilter {
    val country: String?
    val id: Int?
}