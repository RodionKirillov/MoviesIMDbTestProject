package com.example.moviesimdb.di

import com.example.moviesimdb.domain.api.MoviesInteractor
import com.example.moviesimdb.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single <MoviesInteractor>{
        MoviesInteractorImpl(get())
    }
}

