package com.example.skillcinema.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.data.dto.GenreFilterDto
import com.example.skillcinema.domain.KinopoiskUseCase
import com.example.skillcinema.entity.CountryFilter
import com.example.skillcinema.entity.GenreFilter
import com.example.skillcinema.entity.Premier
import com.example.skillcinema.ui.adapter.KinopoiskPagingSource
import com.example.skillcinema.ui.adapter.SearchPagingSource
import com.example.skillcinema.ui.home.FullListFilmsViewModel
import com.example.skillcinema.ui.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: KinopoiskUseCase) : ViewModel() {

    init {
        loadFilter()
    }

    var countries: Int? = null
    var countriesValue = "Любая"
    var genres: Int? = null
    var genreValue = "Любой"
    var type: String = "ALL"
    var yearFrom: Int = 1900
    var yearTo: Int = 2050
    var ratingFrom: Int = 5
    var ratingTo: Int = 10
    var order: String = "RATING"
    var keyword: String = ""

    var genresFilter: List<GenreFilter> = mutableListOf(
        object : GenreFilter {
        override val genre: String? = "Все"
        override val id: Int? = null
    })

    var countryFilter: List<CountryFilter> = mutableListOf(
        object : CountryFilter{
            override val country: String? = "Все"
            override val id: Int? = null
        })

    fun pagedKinopoisk(): Flow<PagingData<Premier>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            SearchPagingSource(
                useCase,
                countries,
                genres,
                type,
                yearFrom,
                yearTo,
                ratingFrom,
                ratingTo,
                order,
                keyword
            )
        }
    ).flow.cachedIn(viewModelScope)

    private fun loadFilter() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getFilters()
            }.fold(
                onSuccess = {
                    genresFilter = genresFilter + it.genres
                    countryFilter = countryFilter + it.countries
                    Log.d(SEARCH_VIEWMODEL_TAG, "все четко")
                },
                onFailure = {
                    Log.d(
                        SEARCH_VIEWMODEL_TAG,
                        it.localizedMessage ?: "тут ошибка"
                    )
                }
            )
        }
    }

    suspend fun checkFilmInWatched(id: Int) : Boolean {
        var ans = false
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getItemFromWatched(id)
            }.fold(
                onSuccess = {
                    ans = it != null && it.kinopoiskId == id
                },
                onFailure = { Log.d(SEARCH_VIEWMODEL_TAG, it.localizedMessage ?: "тут ошибка checkFilmInDB") }
            )
        }.join()
        return ans
    }

    companion object{
        const val SEARCH_VIEWMODEL_TAG = "search viewmodel"
    }
}