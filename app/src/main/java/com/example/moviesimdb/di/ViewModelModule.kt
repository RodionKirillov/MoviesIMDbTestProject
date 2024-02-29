package com.example.moviesimdb.di

import com.example.moviesimdb.presentation.movies.MoviesSearchViewModel
import com.example.moviesimdb.presentation.poster.AboutViewModel
import com.example.moviesimdb.presentation.poster.PosterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MoviesSearchViewModel(get(), get())
    }

    viewModel {(movieId: String) ->
        AboutViewModel(movieId, get())
    }

    viewModel {(posterUrl: String) ->
        PosterViewModel(posterUrl)
    }

}

