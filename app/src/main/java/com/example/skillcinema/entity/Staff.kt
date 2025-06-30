package com.example.skillcinema.entity

import com.example.skillcinema.data.dto.StaffFilmDto

interface Staff {
    val age: Int?
    val birthday: String?
    val birthplace: String?
    val death: String?
    val deathplace: String?
    val facts: List<String>?
    val films: List<StaffFilmDto>
    val growth: Int?
    val hasAwards: Int?
    val nameEn: String?
    val nameRu: String?
    val personId: Int?
    val posterUrl: String?
    val profession: String?
    val sex: String?
    val webUrl: String
}