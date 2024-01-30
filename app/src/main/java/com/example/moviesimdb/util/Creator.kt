package com.example.moviesimdb.util

import android.app.Activity
import android.content.Context
import com.example.moviesimdb.data.MoviesRepositoryImpl
import com.example.moviesimdb.data.network.RetrofitNetworkClient
import com.example.moviesimdb.domain.api.MoviesInteractor
import com.example.moviesimdb.domain.api.MoviesRepository
import com.example.moviesimdb.domain.impl.MoviesInteractorImpl
import com.example.moviesimdb.presentation.movies.MoviesSearchPresenter
import com.example.moviesimdb.presentation.poster.PosterPresenter
import com.example.moviesimdb.presentation.movies.MoviesView
import com.example.moviesimdb.presentation.poster.PosterView

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchPresenter(
        view: MoviesView,
        context: Context,
    ): MoviesSearchPresenter {
        return MoviesSearchPresenter(
            view,
            context,
        )
    }

    fun providePosterPresenter(view: PosterView, imageUrl: String): PosterPresenter {
        return PosterPresenter(view, imageUrl)
    }

}