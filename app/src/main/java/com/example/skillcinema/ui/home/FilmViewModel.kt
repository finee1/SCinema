package com.example.skillcinema.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.db.*
import com.example.skillcinema.data.dto.CountryDto
import com.example.skillcinema.data.dto.FilmFullInfoDto
import com.example.skillcinema.data.dto.SeriesDto
import com.example.skillcinema.domain.KinopoiskUseCase
import com.example.skillcinema.entity.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmViewModel @Inject constructor(
    private val useCase: KinopoiskUseCase
) : ViewModel() {
    private val emptyFilm = FilmFullInfoDto(
        0, "", "", "", "",
        0.0,"", 0, 0, "", "", "", "",
        listOf(CountryDto("")), emptyList(), 2, 2, false, "", ""
    )
    private val emptySerial = SeriesDto(emptyList(), 0)

    private val _filmInfo = MutableStateFlow<FilmFullInfo>(emptyFilm)
    val filmInfo = _filmInfo.asStateFlow()

    private val _staffList = MutableStateFlow<List<StaffListItem>>(emptyList())
    val staffList = _staffList.asStateFlow()

    private val _gallery = MutableStateFlow<List<Image>>(emptyList())
    val gallery = _gallery.asStateFlow()

    private val _similarFilms = MutableStateFlow<List<SimilarFilmPreview>>(emptyList())
    val similarFilms = _similarFilms.asStateFlow()

    private val _serial = MutableStateFlow<SeriesList>(emptySerial)
    val serial = _serial.asStateFlow()

    private val _btnLikeIsChecked = MutableStateFlow(false)
    val btnLikeIsChecked = _btnLikeIsChecked.asStateFlow()

    private val _btnWatched = MutableStateFlow(false)
    val btnWatched = _btnWatched.asStateFlow()

    private val _btnWillWatchIsChecked = MutableStateFlow(false)
    val btnWillWatchIsChecked = _btnWillWatchIsChecked.asStateFlow()

    private val _collectionsWithFilm = MutableStateFlow<List<String>>(emptyList())
    val collectionsWithFilm = _collectionsWithFilm.asStateFlow()

    private val _allLike = MutableStateFlow<List<LikeEntity>>(emptyList())
    val allLike = _allLike.asStateFlow()

    private val _allWantSee = MutableStateFlow<List<WantSeeEntity>>(emptyList())
    val allWantSee = _allWantSee.asStateFlow()


    fun update(id: Int) {
        loadFilm(id)
        loadStaff(id)
        loadGallery(id)
        loadSimilarFilms(id)
        updateCollectionsWithFilm(id)
        updateCountLike()
        updateCountWantSee()
    }

    fun updateSerial(id: Int) {
        loadSerial(id)
    }

    private fun loadFilm(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getFilmInfo(id)
            }.fold(
                onSuccess = {
                    _filmInfo.value = it
                    Log.d(TAG, "все четко film")
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка") }
            )
        }
    }

    private fun loadStaff(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getStaffList(id)
            }.fold(
                onSuccess = {
                    _staffList.value = it
                    Log.d(TAG, "все четко staff")
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка") }
            )
        }
    }

    private fun loadGallery(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getGallery(id, GALLERY_TYPE, FIRST_PAGE)
            }.fold(
                onSuccess = {
                    _gallery.value = it.items
                    Log.d(TAG, "все четко gallery")
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка") }
            )
        }
    }

    private fun loadSimilarFilms(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getSimilarFilms(id)
            }.fold(
                onSuccess = {
                    _similarFilms.value = it.items
                    Log.d(TAG, "все четко gallery")
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка") }
            )
        }
    }

    private fun loadSerial(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getSerial(id)
            }.fold(
                onSuccess = {
                    _serial.value = it
                    Log.d(TAG, "все четко serial")
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка serial") }
            )
        }
    }

    // Обращение к базе данных
    fun addDBLike(film: LikeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.addInLike(film)
                checkFilmInDBLike(film.kinopoiskId!!)
            }
        }
    }

    fun deleteFromDBLike(kinopoiskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.deleteFromLike(kinopoiskId)
                checkFilmInDBLike(kinopoiskId)
            }
        }
    }


    fun checkFilmInDBLike(kinopoiskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getItemFromLike(kinopoiskId)
            }.fold(
                onSuccess = {
                    _btnLikeIsChecked.value = it != null && it.kinopoiskId == kinopoiskId
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка checkFilmInDB") }
            )
        }
    }

    private val allLikeFlow = useCase.getAllLike().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun updateCountLike(){
        viewModelScope.launch {
            kotlin.runCatching {
                allLikeFlow.collect{
                    _allLike.value = it
                }
            }
        }
    }

    fun addDBWantSee(film: WantSeeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.addInWantSee(film)
                checkFilmInDBWantSee(film.kinopoiskId!!)
            }
        }
    }

    fun deleteFromDBWantSee(kinopoiskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.deleteFromWantSee(kinopoiskId)
                checkFilmInDBWantSee(kinopoiskId)
            }
        }
    }

    fun checkFilmInDBWantSee(kinopoiskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getItemFromWantSee(kinopoiskId)
            }.fold(
                onSuccess = {
                    _btnWillWatchIsChecked.value = it != null && it.kinopoiskId == kinopoiskId
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка checkFilmInDB") }
            )
        }
    }

    private val allWantSeeFlow = useCase.getAllWantSee().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun updateCountWantSee(){
        viewModelScope.launch {
            kotlin.runCatching {
                allWantSeeFlow.collect{
                    _allWantSee.value = it
                }
            }
        }
    }

    fun checkFilmInWatched(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getItemFromWatched(id)
            }.fold(
                onSuccess = {
                    _btnWatched.value = it != null && it.kinopoiskId == id
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка checkFilmInDB") }
            )
        }
    }

    fun addInWatched(film: WatchedEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.addInWatched(film)
            }
        }
    }

    fun deleteFromWatched(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.deleteFromWatched(id)
            }
        }
    }

    fun addInInterested(film: InterestedEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.addInInterested(film)
            }
        }
    }

    val allCollections = useCase.getAllTypesCollections().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    suspend fun filmsCount(collection: String): Int {
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

    private fun collectionsWithFilm(kinopoiskId: Int) = useCase.getCollectionsItem(kinopoiskId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun updateCollectionsWithFilm(kinopoiskId: Int){
        viewModelScope.launch {
            kotlin.runCatching {
                collectionsWithFilm(kinopoiskId).collect{
                    _collectionsWithFilm.value = it
                }
            }
        }
    }

    fun addInTypesCollection(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.addInTypesCollections(type)
            }
        }
    }

    fun addInCollection(film: CollectionsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.addInCollection(film)
            }
        }
    }

    fun deleteFromCollection(kinopoiskId: Int, collection: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.deleteFromCollection(kinopoiskId, collection)
            }
        }
    }


    companion object {
        private const val FIRST_PAGE = 1
        private const val GALLERY_TYPE = "STILL"
        private const val TAG = "FilmViewModel"
    }
}