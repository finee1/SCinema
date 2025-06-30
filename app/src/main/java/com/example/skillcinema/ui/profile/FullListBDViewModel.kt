package com.example.skillcinema.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.db.CollectionsEntity
import com.example.skillcinema.domain.KinopoiskUseCase
import com.example.skillcinema.entity.BaseModelDBTable
import com.example.skillcinema.entity.SeriesList
import com.example.skillcinema.ui.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FullListBDViewModel @Inject constructor(
    private val useCase: KinopoiskUseCase
) : ViewModel() {

    private val _collection = MutableStateFlow<List<CollectionsEntity>>(emptyList())
    val collection = _collection.asStateFlow()

    val allWatched = useCase.getAllWatched().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val allInterested = useCase.getAllInterested().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val allLike = useCase.getAllLike().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val allWantSee = useCase.getAllWantSee().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun loadCollection(collection: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getAllCollection(collection)
            }.fold(
                onSuccess = { _collection.value = it },
                onFailure = { Log.d("FullListDB", it.localizedMessage ?: "тут ошибка") }
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
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка checkFilmInDB") }
            )
        }.join()
        return ans
    }

    companion object{
        const val TAG = "FullListBDViewModel"
    }
}