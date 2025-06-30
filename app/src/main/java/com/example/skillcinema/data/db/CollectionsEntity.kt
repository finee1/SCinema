package com.example.skillcinema.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skillcinema.entity.BaseModelDBTable

@Entity(tableName = "Collections")
data class CollectionsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "kinopoiskId")
    override val kinopoiskId: Int,
    @ColumnInfo(name = "genres")
    override val genres: String,
    @ColumnInfo(name = "nameEn")
    override val nameOriginal: String?,
    @ColumnInfo(name = "nameRu")
    override val nameRu: String?,
    @ColumnInfo(name = "posterUrl")
    override val posterUrl: String?,
    @ColumnInfo(name = "ratingKinopoisk")
    override val ratingKinopoisk: Double?,
    @ColumnInfo(name = "collection")
    val collection: String
) : BaseModelDBTable
