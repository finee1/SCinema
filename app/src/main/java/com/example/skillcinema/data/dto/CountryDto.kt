package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.Country
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryDto(
    @Json(name = "country") override val country: String?
) : Country