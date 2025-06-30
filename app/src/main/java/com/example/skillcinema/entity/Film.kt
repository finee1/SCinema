package com.example.skillcinema.entity


interface Film : Premier {
    val nameOriginal: String?
    val ratingKinopoisk: Double?
}
//interface Film {
//    val kinopoiskId: Int?
//    val imdbId: String?
//    val nameRu: String?
//    val nameEn: Any?
//    val nameOriginal: String?
//    val countries: List<Country>?
//    val genres: List<Genre>?
//    val ratingKinopoisk: Double?
//    val ratingImdb: Double?
//    val year: Int?
//    val type: String?
//    val posterUrl: String?
//    val posterUrlPreview: String?
//}