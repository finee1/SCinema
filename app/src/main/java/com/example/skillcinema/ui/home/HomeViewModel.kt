package com.example.skillcinema.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.data.dto.FilmDto
import com.example.skillcinema.data.dto.PremierDto
import com.example.skillcinema.domain.KinopoiskUseCase
import com.example.skillcinema.entity.Film
import com.example.skillcinema.entity.Premier
import com.example.skillcinema.ui.adapter.KinopoiskPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: KinopoiskUseCase) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _premiers = MutableStateFlow<List<Premier>>(emptyList())
    val premiers = _premiers.asStateFlow()

    private val _topPopular = MutableStateFlow<List<Premier>>(emptyList())
    val topPopular = _topPopular.asStateFlow()

    private val _top250 = MutableStateFlow<List<Premier>>(emptyList())
    val top250 = _top250.asStateFlow()

    private val _series = MutableStateFlow<List<Premier>>(emptyList())
    val series = _series.asStateFlow()

    private val _filmsOne = MutableStateFlow<List<Premier>>(emptyList())
    val filmsOne = _filmsOne.asStateFlow()

    private val _filmsTwo = MutableStateFlow<List<Premier>>(emptyList())
    val filmsTwo = _filmsTwo.asStateFlow()

    init {
        loadTop250List()
        loadPremiers()
        loadTopPopularList()
        loadSeries()
    }

    fun updateRandomSelection1(country: Int, genre: Int) {
        loadFilmsOne(country = country, genre = genre)
    }

    fun updateRandomSelection2(country: Int, genre: Int) {
        loadFilmsTwo(country = country, genre = genre)
    }

    private fun loadTop250List() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                _isLoading.value = true
                useCase.getTopList(TOP_250_MOVIES, FIRST_PAGE)
            }.fold(
                onSuccess = {
                    _top250.value = it
                    Log.d(TAG, "все четко")
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка") }
            )
            delay(1000)
            _isLoading.value = false
        }
    }

    private fun loadTopPopularList() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getTopList(TOP_POPULAR_ALL, FIRST_PAGE)
            }.fold(
                onSuccess = {
                    _topPopular.value = it
                    Log.d(TAG, "все четко")
                },
                onFailure = { Log.d(TAG, it.localizedMessage?.plus("popular") ?: "популярное тут ошибка") }
            )
        }
    }

    private fun loadSeries() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getSeries(FIRST_PAGE)
            }.fold(
                onSuccess = {
                    _series.value = it
                    Log.d(TAG, "все четко")
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка") }
            )
        }
    }

    private fun loadPremiers() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getPremiers(
                    SimpleDateFormat("yyyy", Locale.getDefault()).format(Date()).toInt(),
                    currentMonthString(SimpleDateFormat("MM", Locale.getDefault()).format(Date()))
                )
            }.fold(
                onSuccess = {
                    _premiers.value = it
                    Log.d(TAG, "все четко")
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка") }
            )
        }
    }

    private fun loadFilmsOne(country: Int, genre: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getFilms(country, genre, FIRST_PAGE)
            }.fold(
                onSuccess = {
                    _filmsOne.value = it
                    Log.d(TAG, "все четко")
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка") }
            )
        }
    }

    private fun loadFilmsTwo(country: Int, genre: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                _isLoading.value = true
                useCase.getFilms(country, genre, FIRST_PAGE)
            }.fold(
                onSuccess = {
                    _filmsTwo.value = it
                    Log.d(TAG, "все четко")
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка") }
            )
            _isLoading.value = false
        }
    }

    private fun currentMonthString(monthInt: String): String = when (monthInt) {
        "01" -> "JANUARY"
        "02" -> "FEBRUARY"
        "03" -> "MARCH"
        "04" -> "APRIL"
        "05" -> "MAY"
        "06" -> "JUNE"
        "07" -> "JULY"
        "08" -> "AUGUST"
        "09" -> "SEPTEMBER"
        "10" -> "OCTOBER"
        "11" -> "NOVEMBER"
        "12" -> "DECEMBER"
        else -> "JANUARY"
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
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка checkFilmInDB") }
            )
        }.join()
        return ans
    }

    companion object {
        const val FIRST_PAGE = 1
        private const val TAG = "HomeViewModel"
        const val TOP_POPULAR_ALL = "TOP_POPULAR_ALL"
        const val TOP_250_MOVIES = "TOP_250_MOVIES"
    }
}
