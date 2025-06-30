package com.example.skillcinema.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.db.WatchedEntity
import com.example.skillcinema.domain.KinopoiskUseCase
import com.example.skillcinema.ui.home.FilmViewModel
import com.example.skillcinema.ui.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val useCase: KinopoiskUseCase
) : ViewModel() {

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

    val allCollections = useCase.getAllTypesCollections().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val allWantSee = useCase.getAllWantSee().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun addInTypesCollection(type: String){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.addInTypesCollections(type)
            }
        }
    }

    suspend fun filmsCount(collection: String): Int{
        var count = 5
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getAllCollection(collection)
            }.fold(
                onSuccess = { count = it?.size ?: 0 },
                onFailure = { Log.d("Notifications", it.localizedMessage ?: "тут ошибка") }
            )
        }.join()
        return count
    }

    fun clearWatchedDB(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.deleteAllWatched()
            }
        }
    }

    fun clearInterestedDB(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.deleteAllInterested()
            }
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
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка") }
            )
        }.join()
        return ans
    }

    fun deleteCollection(collection: String){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.deleteAllFromCollection(collection)
                useCase.deleteFromTypesCollections(collection)
            }
        }
    }

    companion object{
        const val TAG = "NotificationsViewModel"
    }

}