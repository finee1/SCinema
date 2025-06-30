package com.example.skillcinema.entity

import com.squareup.moshi.Json

interface StaffListItem {
    val description: String?
    val nameEn: String?
    val nameRu: String?
    val posterUrl: String?
    val professionKey: String?
    val professionText: String?
    val staffId: Int?
}