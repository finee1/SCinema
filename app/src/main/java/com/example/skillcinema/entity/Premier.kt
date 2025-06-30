package com.example.skillcinema.entity


interface Premier {
    val genres: List<Genre>?
    val kinopoiskId: Int?
    val year: Int?
    val nameEn: String?
    val nameRu: String?
    val posterUrl: String?
    val posterUrlPreview: String?
}
//interface Premier {
//    val countries: List<Country>?
//    val duration: Int?
//    val genres: List<Genre>?
//    val kinopoiskId: Int?
//    val nameEn: String?
//    val nameRu: String?
//    val posterUrl: String?
//    val posterUrlPreview: String?
//    val premiereRu: String?
//    val year: Int?
//}