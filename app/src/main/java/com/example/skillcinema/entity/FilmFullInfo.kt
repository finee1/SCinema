package com.example.skillcinema.entity

import com.example.skillcinema.data.dto.CountryDto
import com.example.skillcinema.data.dto.GenreDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

interface FilmFullInfo {
    val kinopoiskId: Int?
    val nameRu: String?
    val nameOriginal: String?
    val coverUrl: String?
    val logoUrl: String?
    val posterUrl: String?
    val ratingKinopoisk: Double?
    val webUrl: String?
    val year: Int?
    val filmLength: Int?
    val slogan: String?
    val description: String?
    val shortDescription: String?
    val ratingAgeLimits: String?
    val countries: List<CountryDto>
    val genres: List<GenreDto>
    val startYear: Int?
    val endYear: Int?
    val serial: Boolean?
    val lastSync: String?
}