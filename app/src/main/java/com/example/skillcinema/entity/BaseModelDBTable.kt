package com.example.skillcinema.entity


interface BaseModelDBTable {
    val kinopoiskId: Int?
    val nameRu: String?
    val nameOriginal: String?
    val posterUrl: String?
    val ratingKinopoisk: Double?
    val genres: String?
}