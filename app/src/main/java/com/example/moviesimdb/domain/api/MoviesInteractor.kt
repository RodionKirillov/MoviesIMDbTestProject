package com.example.moviesimdb.domain.api

import com.example.moviesimdb.domain.models.Movie
import com.example.moviesimdb.domain.models.MovieDetails
import com.example.moviesimdb.domain.models.MovieCast
import com.example.moviesimdb.domain.models.Result

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer: MoviesConsumer)
    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

    fun getMovieDetails(movieId: String, consumer: MoviesDetailsConsumer)
    interface MoviesDetailsConsumer {
        fun consume(movieDetails: MovieDetails?, errorMessage: String?)
    }

    fun getMovieFullCast(movieId: String, consumer: MoviesFullCastConsumer)
    interface MoviesFullCastConsumer {
        fun consume(movieFullCast: MovieCast?, errorMessage: String?)
    }

    fun searchName(expression: String, consumer: NameConsumer)
    interface NameConsumer {
        fun consume(foundName: List<Result>?, errorMessage: String?)
    }

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}