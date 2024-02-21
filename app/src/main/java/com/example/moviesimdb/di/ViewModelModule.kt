package com.example.moviesimdb.di

import com.example.moviesimdb.presentation.movies.MoviesSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MoviesSearchViewModel(get(), get())
    }

}

