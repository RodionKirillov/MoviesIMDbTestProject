package com.example.moviesimdb.data

import com.example.moviesimdb.data.converters.MovieCastConverter
import com.example.moviesimdb.data.dto.MovieCastRequest
import com.example.moviesimdb.data.dto.MovieCastResponse
import com.example.moviesimdb.data.dto.MovieDetailsResponse
import com.example.moviesimdb.data.dto.MoviesIdRequest
import com.example.moviesimdb.data.dto.MoviesSearchRequest
import com.example.moviesimdb.data.dto.MoviesSearchResponse
import com.example.moviesimdb.data.dto.NamesSearchRequest
import com.example.moviesimdb.data.dto.NamesSearchResponse
import com.example.moviesimdb.data.memory.LocalStorage
import com.example.moviesimdb.domain.api.MoviesRepository
import com.example.moviesimdb.domain.models.Movie
import com.example.moviesimdb.domain.models.MovieCast
import com.example.moviesimdb.domain.models.MovieDetails
import com.example.moviesimdb.domain.models.Result
import com.example.moviesimdb.util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MovieCastConverter,
) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                val stored = localStorage.getSavedFavorites()

                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(
                        it.id,
                        it.resultType,
                        it.image,
                        it.title,
                        it.description,
                        isFavorite = stored.contains(it.id),
                    )
                })
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun getMovieDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MoviesIdRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                with(response as MovieDetailsResponse) {
                    Resource.Success(
                        MovieDetails(
                            id, title, imDbRating, year,
                            countries, genres, directors, writers, stars, plot
                        )
                    )
                }
            }

            else -> {
                Resource.Error("Ошибка сервера")

            }
        }
    }

    override fun getMovieCast(movieId: String): Resource<MovieCast> {
        val response = networkClient.doRequest(MovieCastRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                Resource.Success(
                    data = movieCastConverter.convert(response as MovieCastResponse)
                )
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun searchName(expression: String): Resource<List<Result>> {
        val response = networkClient.doRequest(NamesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                Resource.Success((response as NamesSearchResponse).results.map {
                    Result(
                        description = it.description,
                        id = it.id,
                        image = it.image,
                        resultType = it.resultType,
                        title = it.title
                    )
                })
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }
}