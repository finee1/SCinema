package com.example.skillcinema.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.dto.SeriesDto
import com.example.skillcinema.domain.KinopoiskUseCase
import com.example.skillcinema.entity.SeriesList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val useCase: KinopoiskUseCase) : ViewModel() {

    private val emptySerial = SeriesDto(emptyList(), 0)

    private val _serial = MutableStateFlow<SeriesList>(emptySerial)
    val serial = _serial.asStateFlow()

    fun updateSerial(id: Int){
        loadSerial(id)
    }

    private fun loadSerial(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getSerial(id)
            }.fold(
                onSuccess = {
                    _serial.value = it
                    Log.d("SeriesVM", "все четко serial")
                },
                onFailure = { Log.d("SeriesVM", it.localizedMessage ?: "тут ошибка serial") }
            )
        }
    }
}