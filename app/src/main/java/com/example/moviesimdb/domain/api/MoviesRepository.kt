package com.example.moviesimdb.domain.api

import com.example.moviesimdb.data.dto.MovieDetailsResponse
import com.example.moviesimdb.domain.models.Movie
import com.example.moviesimdb.domain.models.MovieDetails
import com.example.moviesimdb.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
    fun getMovieDetails(movieId: String): Resource<MovieDetails>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}