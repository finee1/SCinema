package com.example.skillcinema.entity

import com.example.skillcinema.data.dto.EpisodeDto
import com.squareup.moshi.Json

interface Season {
    val episodes: List<Episode>
    val number: Int?
}