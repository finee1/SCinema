package com.example.skillcinema.data

import com.example.skillcinema.data.dto.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class KinopoiskRepository @Inject constructor() {

    suspend fun getTopFilms(type: String, page: Int): List<FilmDto> {
        return RetrofitInstance.retrofit.getTop(type, page).items
    }

    suspend fun getPremiers(year: Int, month: String): List<PremierDto> {
        return RetrofitInstance.retrofit.getPremiers(year, month).items
    }

    suspend fun getSeries(page: Int): List<FilmDto> =
        RetrofitInstance.retrofit.getSeries(page).items

    suspend fun getFilms(
        type: String,
        countries: Int?,
        genres: Int?,
        yearFrom: Int,
        yearTo: Int,
        ratingFrom: Int,
        ratingTo: Int,
        order: String,
        page: Int,
        keyword: String
    ): List<FilmDto> =
        RetrofitInstance.retrofit.getFilms(
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
        ).items

    suspend fun getFilmInfo(id: Int): FilmFullInfoDto = RetrofitInstance.retrofit.getFilmInfo(id)

    suspend fun getStaffList(id: Int): List<StaffListItemDto> =
        RetrofitInstance.retrofit.getStaffList(id)

    suspend fun getGallery(id: Int, type: String, page: Int): ImagesListDto =
        RetrofitInstance.retrofit.getGallery(id, type, page)

    suspend fun getSimilarFilms(id: Int): SimilarListFilmPreviewDto =
        RetrofitInstance.retrofit.getSimilarFilms(id)

    suspend fun getStaff(id: Int): StaffDto = RetrofitInstance.retrofit.getStaff(id)

    suspend fun getSerial(id: Int): SeriesDto = RetrofitInstance.retrofit.getSerial(id)

    suspend fun getFilters(): FilterDto = RetrofitInstance.retrofit.getFilters()

}

object RetrofitInstance {
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech"

    val retrofit: GetTopFilmsKinopoisk = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(GetTopFilmsKinopoisk::class.java)
}

interface GetTopFilmsKinopoisk {


    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/filters")
    suspend fun getFilters(): FilterDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films")
    suspend fun getFilms(
        @Query("type") type: String,
        @Query("countries") countries: Int?,
        @Query("genres") genres: Int?,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("keyword") keyword: String
    ): KinopoiskListFilmsDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films?type=TV_SERIES")
    suspend fun getSeries(
        @Query("page") page: Int
    ): KinopoiskListFilmsDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/collections")
    suspend fun getTop(
        @Query("type") type: String,
        @Query("page") page: Int
    ): KinopoiskListFilmsDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremiers(
        @Query("year") year: Int,
        @Query("month") month: String,
    ): ListPremiersDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmInfo(
        @Path("id") id: Int
    ): FilmFullInfoDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v1/staff")
    suspend fun getStaffList(
        @Query("filmId") filmId: Int
    ): List<StaffListItemDto>

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/{id}/images")
    suspend fun getGallery(
        @Path("id") id: Int,
        @Query("type") type: String,
        @Query("page") page: Int
    ): ImagesListDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilarFilms(
        @Path("id") id: Int,
    ): SimilarListFilmPreviewDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v1/staff/{id}")
    suspend fun getStaff(
        @Path("id") id: Int,
    ): StaffDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun getSerial(
        @Path("id") id: Int,
    ): SeriesDto


    private companion object {
        private const val API_KEY = "26d95390-dd42-4762-a2af-43b49460276f"
    }
}