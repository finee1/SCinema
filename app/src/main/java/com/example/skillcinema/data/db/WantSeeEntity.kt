package com.example.skillcinema.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skillcinema.entity.BaseModelDBTable

@Entity(tableName = "WantSee")
data class WantSeeEntity(
    @PrimaryKey
    @ColumnInfo(name = "kinopoiskId")
    override val kinopoiskId: Int?,
    @ColumnInfo(name = "nameRu")
    override val nameRu: String?,
    @ColumnInfo(name = "nameOriginal")
    override val nameOriginal: String?,
    @ColumnInfo(name = "posterUrl")
    override val posterUrl: String?,
    @ColumnInfo(name = "ratingKinopoisk")
    override val ratingKinopoisk: Double?,
    @ColumnInfo(name = "genres")
    override val genres: String?
) : BaseModelDBTable
