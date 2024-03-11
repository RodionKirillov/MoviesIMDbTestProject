package com.example.moviesimdb.domain.impl

import com.example.moviesimdb.domain.api.MoviesInteractor
import com.example.moviesimdb.domain.api.MoviesRepository
import com.example.moviesimdb.domain.models.Movie
import com.example.moviesimdb.util.Resource
import java.util.concurrent.Executors

class MoviesInteractorImpl(
    private val repository: MoviesRepository
) : MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            when(val resource = repository.searchMovies(expression)) {
                is Resource.Success -> { consumer.consume(resource.data, null) }
                is Resource.Error -> { consumer.consume(null, resource.message) }
            }
        }
    }

    override fun getMovieDetails(movieId: String, consumer: MoviesInteractor.MoviesDetailsConsumer) {
       executor.execute {
           when(val resource = repository.getMovieDetails(movieId)) {
               is Resource.Success -> {consumer.consume(resource.data, null)}
               is Resource.Error -> {consumer.consume(null, resource.message)}
           }
       }
    }

    override fun getMovieFullCast(movieId: String, consumer: MoviesInteractor.MoviesFullCastConsumer) {
        executor.execute {
            when(val resource = repository.getMovieCast(movieId)) {
                is Resource.Success -> {consumer.consume(resource.data,null)}
                is Resource.Error -> {consumer.consume(null, resource.message)}
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        repository.addMovieToFavorites(movie)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        repository.removeMovieFromFavorites(movie)
    }
}