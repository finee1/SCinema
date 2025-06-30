package com.example.skillcinema.entity

import com.squareup.moshi.Json

interface SimilarFilmPreview {
    val filmId: Int?
    val nameEn: String?
    val nameOriginal: String?
    val nameRu: String?
    val posterUrl: String?
    val posterUrlPreview: String?
    val relationType: String?
}