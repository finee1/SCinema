package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreDto(
    @Json(name = "genre") override val genre: String?
) : Genre
