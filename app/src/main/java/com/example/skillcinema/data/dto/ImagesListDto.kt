package com.example.skillcinema.data.dto
import com.example.skillcinema.entity.Image
import com.example.skillcinema.entity.ImagesList
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ImagesListDto(
    @Json(name = "items")
    override val items: List<ImageDto>,
) : ImagesList

@JsonClass(generateAdapter = true)
data class ImageDto(
    @Json(name = "imageUrl")
    override val imageUrl: String?,
    @Json(name = "previewUrl")
    override val previewUrl: String?
) : Image