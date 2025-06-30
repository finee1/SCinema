package com.example.skillcinema.ui.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.domain.KinopoiskUseCase
import com.example.skillcinema.entity.Premier
import com.example.skillcinema.ui.home.FullListFilmsFragment
import com.example.skillcinema.ui.home.HomeFragment
import com.example.skillcinema.ui.home.HomeViewModel

class SearchPagingSource(
    private val useCase: KinopoiskUseCase,
    private val countries: Int?,
    private val genres: Int?,
    private val type: String,
    private val yearFrom: Int,
    private val yearTo: Int,
    private val ratingFrom: Int,
    private val ratingTo: Int,
    private val order: String,
    private val keyword: String
) :
    PagingSource<Int, Premier>() {
    override fun getRefreshKey(state: PagingState<Int, Premier>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Premier> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            useCase.getFilms(countries, genres, page, type, yearFrom, yearTo, ratingFrom, ratingTo, order, keyword)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            }, onFailure = { LoadResult.Error(it) }
        )
    }

    private companion object {
        const val FIRST_PAGE = 1
    }
}