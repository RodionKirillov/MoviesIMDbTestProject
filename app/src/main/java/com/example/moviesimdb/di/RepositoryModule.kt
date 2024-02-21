package com.example.moviesimdb.di

import com.example.moviesimdb.data.MoviesRepositoryImpl
import com.example.moviesimdb.domain.api.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get())
    }

}