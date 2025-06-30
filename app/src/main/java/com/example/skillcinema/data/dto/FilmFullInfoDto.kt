package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.FilmFullInfo
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class FilmFullInfoDto(
    @Json(name = "kinopoiskId")
    override val kinopoiskId: Int?,
    @Json(name = "nameRu")
    override val nameRu: String?,
    @Json(name = "nameOriginal")
    override val nameOriginal: String?,
    @Json(name = "coverUrl")
    override val coverUrl: String?,
    @Json(name = "logoUrl")
    override val logoUrl: String?,
    @Json(name = "ratingKinopoisk")
    override val ratingKinopoisk: Double?,
    @Json(name = "webUrl")
    override val webUrl: String?,
    @Json(name = "year")
    override val year: Int?,
    @Json(name = "filmLength")
    override val filmLength: Int?,
    @Json(name = "slogan")
    override val slogan: String?,
    @Json(name = "description")
    override val description: String?,
    @Json(name = "shortDescription")
    override val shortDescription: String?,
    @Json(name = "ratingAgeLimits")
    override val ratingAgeLimits: String?,
    @Json(name = "countries")
    override val countries: List<CountryDto>,
    @Json(name = "genres")
    override val genres: List<GenreDto>,
    @Json(name = "startYear")
    override val startYear: Int?,
    @Json(name = "endYear")
    override val endYear: Int?,
    @Json(name = "serial")
    override val serial: Boolean,
    @Json(name = "lastSync")
    override val lastSync: String?,
    @Json(name = "posterUrl")
    override val posterUrl: String?
) : FilmFullInfo
