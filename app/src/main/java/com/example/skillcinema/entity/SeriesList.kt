package com.example.skillcinema.entity

import com.squareup.moshi.Json

interface SeriesList {
    val items: List<Season>
    val total: Int
}