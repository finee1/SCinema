package com.example.skillcinema.ui.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.domain.KinopoiskUseCase
import com.example.skillcinema.entity.Premier
import com.example.skillcinema.ui.home.FullListFilmsFragment
import com.example.skillcinema.ui.home.HomeFragment
import com.example.skillcinema.ui.home.HomeViewModel

class KinopoiskPagingSource(private val useCase: KinopoiskUseCase, val key: String) :
    PagingSource<Int, Premier>() {
    override fun getRefreshKey(state: PagingState<Int, Premier>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Premier> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            when (key) {
                FullListFilmsFragment.SERIES_KEY -> {
                    useCase.getSeries(page)
                }
                FullListFilmsFragment.PREMIERES_KEY -> {
                    useCase.getPremiers(2024, "FEBRUARY")
                }
                FullListFilmsFragment.TOP250_KEY -> {
                    useCase.getTopList(
                        HomeViewModel.TOP_250_MOVIES,
                        page
                    )
                }
                FullListFilmsFragment.TOP_POPULAR_KEY -> {
                    useCase.getTopList(TOP_POPULAR_ALL, page)
                }
                FullListFilmsFragment.RANDOM_SELECTION_ONE_KEY -> {
                    useCase.getFilms(HomeFragment.firstSelectionValue.country, HomeFragment.firstSelectionValue.genre, page)
                }
                FullListFilmsFragment.RANDOM_SELECTION_TWO_KEY -> {
                    useCase.getFilms(HomeFragment.secondSelectionValue.country, HomeFragment.secondSelectionValue.genre, page)
                }
                else -> {
                    throw IllegalArgumentException("unknown view type $key")
                }
            }
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
        private const val TOP_POPULAR_ALL = "TOP_POPULAR_ALL"
    }
}