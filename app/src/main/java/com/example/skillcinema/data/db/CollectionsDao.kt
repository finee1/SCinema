package com.example.skillcinema.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CollectionsDao {

    //    Collection
    @Query("SELECT * FROM Collections WHERE collection = :collectionKey")
    suspend fun getAllFromCollection(collectionKey: String): List<CollectionsEntity>

    @Query("SELECT * FROM Collections WHERE collection = :collection AND kinopoiskId = :kinopoiskId")
    suspend fun getItemFromCollection(kinopoiskId: Int, collection: String): CollectionsEntity?

    @Query("DELETE FROM Collections WHERE kinopoiskId = :kinopoiskId AND collection = :collectionKey")
    suspend fun deleteItemFromCollection(kinopoiskId: Int, collectionKey: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInCollection(film: CollectionsEntity)

    @Query("SELECT collection FROM Collections WHERE kinopoiskId = :kinopoiskId")
    fun getCollectionsItem(kinopoiskId: Int): Flow<List<String>>

    @Query("DELETE FROM Collections WHERE collection = :collection")
    suspend fun deleteAllFromCollection(collection: String)

    //    Watched
    @Query("SELECT * FROM Watched WHERE kinopoiskId = :kinopoiskId")
    suspend fun getItemFromWatched(kinopoiskId: Int): WatchedEntity?

    @Query("SELECT * FROM Watched")
    fun getAllFromWatched(): Flow<List<WatchedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInWatched(film: WatchedEntity)

    @Query("DELETE FROM Watched WHERE kinopoiskId = :kinopoiskId")
    suspend fun deleteFromWatched(kinopoiskId: Int)

    @Query("DELETE FROM Watched")
    suspend fun deleteAllFromWatched()

    //    Interested
    @Query("SELECT * FROM Interested")
    fun getAllFromInterested(): Flow<List<InterestedEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInInterested(film: InterestedEntity)

    @Query("DELETE FROM Interested")
    suspend fun deleteAllInterested()

    //    Like
    @Query("SELECT * FROM `Like` WHERE kinopoiskId = :kinopoiskId")
    suspend fun getItemFromLike(kinopoiskId: Int): LikeEntity?

    @Query("SELECT * FROM `Like`")
    fun getAllFromLike(): Flow<List<LikeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInLike(film: LikeEntity)

    @Query("DELETE FROM `Like` WHERE kinopoiskId = :kinopoiskId")
    suspend fun deleteFromLike(kinopoiskId: Int)

    @Query("DELETE FROM `Like`")
    suspend fun deleteAllFromLike()

    //    WantSee
    @Query("SELECT * FROM WantSee WHERE kinopoiskId = :kinopoiskId")
    suspend fun getItemFromWantSee(kinopoiskId: Int): WantSeeEntity?

    @Query("SELECT * FROM WantSee")
    fun getAllFromWantSee(): Flow<List<WantSeeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInWantSee(film: WantSeeEntity)

    @Query("DELETE FROM WantSee WHERE kinopoiskId = :kinopoiskId")
    suspend fun deleteFromWantSee(kinopoiskId: Int)

    @Query("DELETE FROM WantSee")
    suspend fun deleteAllFromWantSee()

    //Types Collections

    @Query("SELECT typeName FROM TypesCollections")
    fun getAllFromTypesCollections(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInTypesCollections(type: TypesCollections)

    @Query("DELETE FROM TypesCollections WHERE typeName = :typeName")
    suspend fun deleteFromTypesCollections(typeName: String)
}