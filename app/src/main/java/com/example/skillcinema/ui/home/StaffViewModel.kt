package com.example.skillcinema.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.dto.StaffDto
import com.example.skillcinema.data.dto.StaffFilmDto
import com.example.skillcinema.domain.KinopoiskUseCase
import com.example.skillcinema.entity.SimilarFilmPreview
import com.example.skillcinema.entity.Staff
import com.squareup.moshi.Json
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffViewModel @Inject constructor(private val useCase: KinopoiskUseCase) : ViewModel() {

    private val emptyStaff = StaffDto(
        0, "", "", "", "", emptyList(), emptyList(), 0,
        0, "", "", 0, "", "", "", ""
    )

    private val _staff = MutableStateFlow<Staff>(emptyStaff)
    val staff = _staff.asStateFlow()

    fun update(id: Int){
        loadStaff(id)
    }

    private fun loadStaff(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                useCase.getStaff(id)
            }.fold(
                onSuccess = {
                    _staff.value = it
                    Log.d(TAG, "все четко")
                },
                onFailure = { Log.d(TAG, it.localizedMessage ?: "тут ошибка") }
            )
        }
    }

    companion object {
        private const val TAG = "StaffViewModel"
    }
}