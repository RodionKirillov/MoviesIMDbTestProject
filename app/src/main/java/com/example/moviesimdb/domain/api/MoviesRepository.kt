package com.example.moviesimdb.domain.api

import com.example.moviesimdb.domain.models.Movie
import com.example.moviesimdb.domain.models.MovieDetails
import com.example.moviesimdb.domain.models.MovieCast
import com.example.moviesimdb.domain.models.Result
import com.example.moviesimdb.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun searchMovies(expression: String): Flow<Resource<List<Movie>>>
    fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>>
    fun getMovieCast(movieId: String): Flow<Resource<MovieCast>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
    fun searchName(expression: String): Flow<Resource<List<Result>>>
}