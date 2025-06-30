package com.example.skillcinema.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class FilmViewModelFactory @Inject constructor(private val filmViewModel: FilmViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmViewModel::class.java)){
            return filmViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}