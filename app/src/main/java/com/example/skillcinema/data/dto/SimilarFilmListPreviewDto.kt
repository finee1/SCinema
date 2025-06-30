package com.example.skillcinema.data.dto
import com.example.skillcinema.entity.SimilarFilmPreview
import com.example.skillcinema.entity.SimilarListFilmPreview
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class SimilarListFilmPreviewDto(
    @Json(name = "items")
    override val items: List<SimilarFilmPreviewDto>
): SimilarListFilmPreview

@JsonClass(generateAdapter = true)
data class SimilarFilmPreviewDto(
    @Json(name = "filmId")
    override val filmId: Int?,
    @Json(name = "nameEn")
    override val nameEn: String?,
    @Json(name = "nameOriginal")
    override val nameOriginal: String?,
    @Json(name = "nameRu")
    override val nameRu: String?,
    @Json(name = "posterUrl")
    override val posterUrl: String?,
    @Json(name = "posterUrlPreview")
    override val posterUrlPreview: String?,
    @Json(name = "relationType")
    override val relationType: String?
): SimilarFilmPreview