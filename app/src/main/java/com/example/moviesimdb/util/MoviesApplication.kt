package com.example.moviesimdb.util

import android.app.Application
import com.example.moviesimdb.di.dataModule
import com.example.moviesimdb.di.interactorModule
import com.example.moviesimdb.di.repositoryModule
import com.example.moviesimdb.di.viewModelModule
import com.example.moviesimdb.presentation.movies.MoviesSearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication : Application() {

    var moviesSearchViewModel : MoviesSearchViewModel? = null

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApplication)
            modules(dataModule, interactorModule, repositoryModule, viewModelModule)
        }
    }
}