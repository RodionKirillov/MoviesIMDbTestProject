package com.example.moviesimdb.util

import android.app.Activity
import android.content.Context
import com.example.moviesimdb.data.MoviesRepositoryImpl
import com.example.moviesimdb.data.network.RetrofitNetworkClient
import com.example.moviesimdb.domain.api.MoviesInteractor
import com.example.moviesimdb.domain.api.MoviesRepository
import com.example.moviesimdb.domain.impl.MoviesInteractorImpl
import com.example.moviesimdb.presentation.MoviesSearchController
import com.example.moviesimdb.presentation.PosterController
import com.example.moviesimdb.ui.movies.MoviesAdapter

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchController(
        activity: Activity,
        adapter: MoviesAdapter
    ): MoviesSearchController {
        return MoviesSearchController(activity, adapter)
    }

    fun providePosterController(activity: Activity): PosterController {
        return PosterController(activity)
    }

}