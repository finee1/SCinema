package com.example.skillcinema.domain

import com.example.skillcinema.data.db.CollectionsDao
import com.example.skillcinema.data.db.CollectionsEntity
import javax.inject.Inject

//class DataUseCase @Inject constructor(
//    private val db: CollectionsDao
//) {
//    suspend fun getAllCollection(collectionKey: String): List<CollectionsEntity> =
//        db.getAll(collectionKey)
//
//    suspend fun addInCollection(film: CollectionsEntity) = db.insert(film)
//
//    suspend fun deleteFromCollection(film: CollectionsEntity) = db.delete(film)
//}