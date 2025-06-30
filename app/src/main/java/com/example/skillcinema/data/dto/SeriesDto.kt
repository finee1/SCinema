package com.example.skillcinema.data.dto
import com.example.skillcinema.entity.Episode
import com.example.skillcinema.entity.Season
import com.example.skillcinema.entity.SeriesList
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class SeriesDto(
    @Json(name = "items")
    override val items: List<SeasonDto>,
    @Json(name = "total")
    override val total: Int
) : SeriesList

@JsonClass(generateAdapter = true)
data class SeasonDto(
    @Json(name = "episodes")
    override val episodes: List<EpisodeDto>,
    @Json(name = "number")
    override val number: Int?
) : Season

@JsonClass(generateAdapter = true)
data class EpisodeDto(
    @Json(name = "seasonNumber")
    override val seasonNumber: Int?,
    @Json(name = "episodeNumber")
    override val episodeNumber: Int?,
    @Json(name = "nameRu")
    override val nameRu: String?,
    @Json(name = "nameEn")
    override val nameEn: String?,
    @Json(name = "synopsis")
    override val synopsis: String?,
    @Json(name = "releaseDate")
    override val releaseDate: String?
) : Episode