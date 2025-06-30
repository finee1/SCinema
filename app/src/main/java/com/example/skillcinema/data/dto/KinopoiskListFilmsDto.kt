package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.KinopoiskListFilms
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KinopoiskListFilmsDto(
    @Json(name = "items") override val items: List<FilmDto>
) : KinopoiskListFilms
//@JsonClass(generateAdapter = true)
//data class KinopoiskListFilmsDto(
//    @Json(name = "total") override val total: Int?,
//    @Json(name = "totalPages") override val totalPages: Int?,
//    @Json(name = "items") override val items: List<FilmDto>
//) : KinopoiskListFilms
