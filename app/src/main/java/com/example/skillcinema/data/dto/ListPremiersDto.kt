package com.example.skillcinema.data.dto
import com.example.skillcinema.entity.ListPremiers
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class ListPremiersDto(
    @Json(name = "items")
    override val items: List<PremierDto>,
) : ListPremiers