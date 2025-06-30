package com.example.skillcinema.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.domain.KinopoiskUseCase
import com.example.skillcinema.entity.Film
import com.example.skillcinema.entity.Premier
import com.example.skillcinema.ui.adapter.KinopoiskPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FullListFilmsViewModel @Inject constructor(private val useCase: KinopoiskUseCase) : ViewModel() {

    fun pagedKinopoisk(key: String): Flow<PagingData<Premier>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { KinopoiskPagingSource(useCase, key) }
    ).flow.cachedIn(viewModelScope)

    private val _premiers = MutableStateFlow<List<Premier>>(emptyList())
    val premiers = _premiers.asStateFlow()


    fun updatePremiers(){
        loadPremiers()
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

    companion object{
        private const val TAG = "FullListFilmsViewModel"
    }
}