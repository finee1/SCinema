package com.example.skillcinema.data.db

import javax.inject.Inject

class DataRepository @Inject constructor(private val dao: CollectionsDao) {

    suspend fun getAllCollection(collectionKey: String): List<CollectionsEntity>? =
        dao.getAllFromCollection(collectionKey)

    suspend fun addInCollection(film: CollectionsEntity) = dao.insertInCollection(film)


}