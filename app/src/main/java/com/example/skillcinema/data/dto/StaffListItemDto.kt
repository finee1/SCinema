package com.example.skillcinema.data.dto
import com.example.skillcinema.entity.StaffListItem
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class StaffListItemDto(
    @Json(name = "description")
    override val description: String?,
    @Json(name = "nameEn")
    override val nameEn: String?,
    @Json(name = "nameRu")
    override val nameRu: String?,
    @Json(name = "posterUrl")
    override val posterUrl: String?,
    @Json(name = "professionKey")
    override val professionKey: String?,
    @Json(name = "professionText")
    override val professionText: String?,
    @Json(name = "staffId")
    override val staffId: Int?
) : StaffListItem
