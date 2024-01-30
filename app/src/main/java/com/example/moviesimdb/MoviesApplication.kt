package com.example.moviesimdb

import android.app.Application
import com.example.moviesimdb.presentation.movies.MoviesSearchPresenter

class MoviesApplication : Application() {

    var moviesSearchPresenter : MoviesSearchPresenter? = null

}