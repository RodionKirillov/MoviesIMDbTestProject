package com.example.moviesimdb.util

import android.app.Application
import android.content.Context
import com.example.moviesimdb.data.MoviesRepositoryImpl
import com.example.moviesimdb.data.memory.LocalStorage
import com.example.moviesimdb.data.network.RetrofitNetworkClient
import com.example.moviesimdb.domain.api.MoviesInteractor
import com.example.moviesimdb.domain.api.MoviesRepository
import com.example.moviesimdb.domain.impl.MoviesInteractorImpl
import com.example.moviesimdb.presentation.movies.MoviesSearchViewModel
import com.example.moviesimdb.presentation.poster.PosterPresenter
import com.example.moviesimdb.presentation.poster.PosterView

object Creator {

    fun providePosterPresenter(view: PosterView, imageUrl: String): PosterPresenter {
        return PosterPresenter(view, imageUrl)
    }

}