package com.example.skillcinema.ui.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.domain.KinopoiskUseCase
import com.example.skillcinema.entity.Image
import com.example.skillcinema.entity.Premier
import com.example.skillcinema.ui.home.FullListFilmsFragment
import com.example.skillcinema.ui.home.HomeFragment
import com.example.skillcinema.ui.home.HomeViewModel

class GalleryPagingSource(
    private val useCase: KinopoiskUseCase,
    private val id: Int,
    private val type: String
) :
    PagingSource<Int, Image>() {
    override fun getRefreshKey(state: PagingState<Int, Image>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            useCase.getGallery(id, type, page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it.items,
                    prevKey = null,
                    nextKey = if (it.items.isEmpty()) null else page + 1
                )
            }, onFailure = { LoadResult.Error(it) }
        )
    }

    private companion object {
        const val FIRST_PAGE = 1
    }
}