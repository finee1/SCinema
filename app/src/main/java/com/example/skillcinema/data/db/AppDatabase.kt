package com.example.skillcinema.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CollectionsEntity::class,
        WatchedEntity::class,
        InterestedEntity::class,
        LikeEntity::class,
        WantSeeEntity::class,
        TypesCollections::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): CollectionsDao
}