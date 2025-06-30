package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.Premier
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PremierDto(
    @Json(name = "genres")
    override val genres: List<GenreDto>?,
    @Json(name = "kinopoiskId")
    override val kinopoiskId: Int?,
    @Json(name = "nameEn")
    override val nameEn: String?,
    @Json(name = "nameRu")
    override val nameRu: String?,
    @Json(name = "posterUrl")
    override val posterUrl: String?,
    @Json(name = "posterUrlPreview")
    override val posterUrlPreview: String?,
    @Json(name = "year")
    override val year: Int?
): Premier
