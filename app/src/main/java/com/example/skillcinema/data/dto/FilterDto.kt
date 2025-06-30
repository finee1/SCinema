package com.example.skillcinema.data.dto
import com.example.skillcinema.entity.CountryFilter
import com.example.skillcinema.entity.Filter
import com.example.skillcinema.entity.GenreFilter
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class FilterDto(
    @Json(name = "genres")
    override val genres: List<GenreFilterDto>,
    @Json(name = "countries")
    override val countries: List<CountryFilterDto>,
) : Filter

@JsonClass(generateAdapter = true)
data class CountryFilterDto(
    @Json(name = "country")
    override val country: String?,
    @Json(name = "id")
    override val id: Int?
) : CountryFilter

@JsonClass(generateAdapter = true)
data class GenreFilterDto(
    @Json(name = "genre")
    override val genre: String?,
    @Json(name = "id")
    override val id: Int?
) : GenreFilter
