package com.example.moviesimdb

import android.app.Application
import com.example.moviesimdb.presentation.movies.MoviesSearchViewModel

class MoviesApplication : Application() {

    var moviesSearchViewModel : MoviesSearchViewModel? = null

}