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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MovieCastConverter,
) : MoviesRepository {

    override fun searchMovies(expression: String): Flow<Resource<List<Movie>>> = flow {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                val stored = localStorage.getSavedFavorites()

                emit(Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(
                        it.id,
                        it.resultType,
                        it.image,
                        it.title,
                        it.description,
                        isFavorite = stored.contains(it.id),
                    )
                }))
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    override fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>> = flow {
        val response = networkClient.doRequest(MoviesIdRequest(movieId))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                with(response as MovieDetailsResponse) {
                    emit(
                        Resource.Success(
                            MovieDetails(
                                id, title, imDbRating, year,
                                countries, genres, directors, writers, stars, plot
                            )
                        )
                    )
                }
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    override fun getMovieCast(movieId: String): Flow<Resource<MovieCast>> = flow {
        val response = networkClient.doRequest(MovieCastRequest(movieId))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                emit(
                    Resource.Success(
                        data = movieCastConverter.convert(response as MovieCastResponse)
                    )
                )
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    override fun searchName(expression: String): Flow<Resource<List<Result>>> = flow {
        val response = networkClient.doRequest(NamesSearchRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                emit(
                    Resource.Success((response as NamesSearchResponse).results.map {
                        Result(
                            description = it.description,
                            id = it.id,
                            image = it.image,
                            resultType = it.resultType,
                            title = it.title
                        )
                    })
                )
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
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