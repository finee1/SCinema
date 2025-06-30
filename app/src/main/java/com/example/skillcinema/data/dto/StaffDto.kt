package com.example.skillcinema.data.dto

import android.os.Parcelable
import com.example.skillcinema.entity.Staff
import com.example.skillcinema.entity.StaffFilm
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@JsonClass(generateAdapter = true)
data class StaffDto(
    @Json(name = "age")
    override val age: Int?,
    @Json(name = "birthday")
    override val birthday: String?,
    @Json(name = "birthplace")
    override val birthplace: String?,
    @Json(name = "death")
    override val death: String?,
    @Json(name = "deathplace")
    override val deathplace: String?,
    @Json(name = "facts")
    override val facts: List<String>?,
    @Json(name = "films")
    override val films: List<StaffFilmDto>,
    @Json(name = "growth")
    override val growth: Int?,
    @Json(name = "hasAwards")
    override val hasAwards: Int?,
    @Json(name = "nameEn")
    override val nameEn: String?,
    @Json(name = "nameRu")
    override val nameRu: String?,
    @Json(name = "personId")
    override val personId: Int?,
    @Json(name = "posterUrl")
    override val posterUrl: String?,
    @Json(name = "profession")
    override val profession: String?,
    @Json(name = "sex")
    override val sex: String?,
    @Json(name = "webUrl")
    override val webUrl: String
) : Staff

@Parcelize
@JsonClass(generateAdapter = true)
data class StaffFilmDto (
    @Json(name = "description")
    override val description: String?,
    @Json(name = "filmId")
    override val filmId: Int?,
    @Json(name = "general")
    override val general: Boolean?,
    @Json(name = "nameEn")
    override val nameEn: String?,
    @Json(name = "nameRu")
    override val nameRu: String?,
    @Json(name = "professionKey")
    override val professionKey: String?,
    @Json(name = "rating")
    override val rating: String?
) : StaffFilm, Parcelable