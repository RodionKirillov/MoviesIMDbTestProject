package com.example.moviesimdb.domain.api

import com.example.moviesimdb.domain.models.Movie
import com.example.moviesimdb.domain.models.MovieDetails
import com.example.moviesimdb.domain.models.MovieCast
import com.example.moviesimdb.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface MoviesInteractor {
    fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>>

    fun getMovieDetails(movieId: String): Flow<Pair<MovieDetails?, String?>>

    fun getMovieFullCast(movieId: String): Flow<Pair<MovieCast?, String?>>

    fun searchName(expression: String): Flow<Pair<List<Result>?, String?>>

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}