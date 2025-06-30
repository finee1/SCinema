package com.example.skillcinema.domain

import com.example.skillcinema.data.KinopoiskRepository
import com.example.skillcinema.data.db.*
import com.example.skillcinema.entity.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KinopoiskUseCase @Inject constructor(
    private val repository: KinopoiskRepository,
    private val data: CollectionsDao
) {

    // Запросы в базу данных

    // Collections
    suspend fun getAllCollection(collectionKey: String): List<CollectionsEntity> =
        data.getAllFromCollection(collectionKey)

    fun getCollectionsItem(collectionKey: Int): Flow<List<String>> =
        data.getCollectionsItem(collectionKey)

    suspend fun addInCollection(film: CollectionsEntity) = data.insertInCollection(film)

    suspend fun deleteFromCollection(kinopoiskId: Int, collection: String) = data.deleteItemFromCollection(kinopoiskId, collection)

    suspend fun deleteAllFromCollection(collection: String) = data.deleteAllFromCollection(collection)

    // Watched
    fun getAllWatched(): Flow<List<WatchedEntity>> = data.getAllFromWatched()

    suspend fun addInWatched(film: WatchedEntity) = data.insertInWatched(film)

    suspend fun deleteFromWatched(id: Int) = data.deleteFromWatched(id)

    suspend fun getItemFromWatched(id: Int) = data.getItemFromWatched(id)

    suspend fun deleteAllWatched() = data.deleteAllFromWatched()

    //Interested

    fun getAllInterested(): Flow<List<InterestedEntity>> = data.getAllFromInterested()

    suspend fun addInInterested(film: InterestedEntity) = data.insertInInterested(film)

    suspend fun deleteAllInterested() = data.deleteAllInterested()

    // Like
    fun getAllLike(): Flow<List<LikeEntity>> = data.getAllFromLike()

    suspend fun addInLike(film: LikeEntity) = data.insertInLike(film)

    suspend fun deleteFromLike(id: Int) = data.deleteFromLike(id)

    suspend fun getItemFromLike(id: Int) = data.getItemFromLike(id)

    // Want See
    fun getAllWantSee(): Flow<List<WantSeeEntity>> = data.getAllFromWantSee()

    suspend fun addInWantSee(film: WantSeeEntity) = data.insertInWantSee(film)

    suspend fun deleteFromWantSee(id: Int) = data.deleteFromWantSee(id)

    suspend fun getItemFromWantSee(id: Int) = data.getItemFromWantSee(id)

    //TypesCollections

    fun getAllTypesCollections(): Flow<List<String>> = data.getAllFromTypesCollections()

    suspend fun addInTypesCollections(type: String) = data.insertInTypesCollections(TypesCollections(typeName = type))

    suspend fun deleteFromTypesCollections(type: String) = data.deleteFromTypesCollections(type)

    //Запросы на сервер
    suspend fun getTopList(type: String, page: Int): List<Premier> =
        repository.getTopFilms(type, page)

    suspend fun getPremiers(year: Int, month: String): List<Premier> =
        repository.getPremiers(year, month)

    suspend fun getSeries(page: Int): List<Premier> = repository.getSeries(page)

    suspend fun getFilms(
        countries: Int?,
        genres: Int?,
        page: Int,
        type: String = "ALL",
        yearFrom: Int = 1000,
        yearTo: Int = 3000,
        ratingFrom: Int = 0,
        ratingTo: Int = 10,
        order: String = "RATING",
        keyword: String = ""
    ): List<Premier> =
        repository.getFilms(
            type,
            countries,
            genres,
            yearFrom,
            yearTo,
            ratingFrom,
            ratingTo,
            order,
            page,
            keyword
        )

    suspend fun getFilmInfo(id: Int): FilmFullInfo = repository.getFilmInfo(id)

    suspend fun getStaffList(id: Int): List<StaffListItem> = repository.getStaffList(id)

    suspend fun getGallery(id: Int, type: String, page: Int): ImagesList =
        repository.getGallery(id, type, page)

    suspend fun getSimilarFilms(id: Int): SimilarListFilmPreview = repository.getSimilarFilms(id)

    suspend fun getStaff(id: Int): Staff = repository.getStaff(id)

    suspend fun getSerial(id: Int): SeriesList = repository.getSerial(id)

    suspend fun getFilters(): Filter = repository.getFilters()
}