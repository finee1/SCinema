package com.example.skillcinema.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.domain.KinopoiskUseCase
import com.example.skillcinema.entity.Image
import com.example.skillcinema.ui.adapter.GalleryPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val useCase: KinopoiskUseCase): ViewModel() {

    fun pagedGallery(id: Int, type: String): Flow<PagingData<Image>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { GalleryPagingSource(useCase, id, type) }
    ).flow.cachedIn(viewModelScope)
}