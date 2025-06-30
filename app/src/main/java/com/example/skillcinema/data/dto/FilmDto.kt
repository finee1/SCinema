package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.Film
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class FilmDto(
    @Json(name = "kinopoiskId") override val kinopoiskId: Int?,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "nameOriginal") override val nameOriginal: String?,
    @Json(name = "genres") override val genres: List<GenreDto>?,
    @Json(name = "ratingKinopoisk") override val ratingKinopoisk: Double?,
    @Json(name = "posterUrl") override val posterUrl: String?,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String?,
    @Json(name = "year") override val year: Int?
) : Film