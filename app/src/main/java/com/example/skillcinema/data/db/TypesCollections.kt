package com.example.skillcinema.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TypesCollections")
data class TypesCollections(
    @PrimaryKey
    @ColumnInfo(name = "typeName")
    val typeName: String,
)
