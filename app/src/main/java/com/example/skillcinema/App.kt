package com.example.skillcinema

import android.app.Application
import androidx.room.Room
import com.example.skillcinema.data.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application()