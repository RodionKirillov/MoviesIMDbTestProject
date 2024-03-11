package com.example.moviesimdb.di

import com.example.moviesimdb.data.MoviesRepositoryImpl
import com.example.moviesimdb.data.converters.MovieCastConverter
import com.example.moviesimdb.domain.api.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {

    // Добавили фабрику для конвертера
    factory { MovieCastConverter() }

    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get(), get())
    }

}